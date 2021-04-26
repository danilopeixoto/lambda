package com.danilopeixoto.app;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.javaparser.StaticJavaParser;
import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.visitor.VoidVisitor;
import io.r2dbc.postgresql.codec.Json;
import reactor.util.function.Tuple3;
import reactor.util.function.Tuples;

import javax.tools.*;
import java.io.*;
import java.lang.reflect.Method;
import java.net.URI;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.*;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

class Application {
  public static Tuple3<JsonNode, String, Integer> execute(String source, List<JsonNode> arguments) {
    PrintStream standardOutput = System.out;
    ObjectMapper objectMapper = new ObjectMapper();
    VoidVisitor<List<String>> publicClassNameCollector = new PublicClassNameCollector();
    JavaCompiler compiler = ToolProvider.getSystemJavaCompiler();
    DiagnosticCollector<JavaFileObject> diagnostics = new DiagnosticCollector<>();

    CompilationUnit compilationUnit = StaticJavaParser.parse(source);
    List<String> publicClassNames = new ArrayList<>();

    publicClassNameCollector.visit(compilationUnit, publicClassNames);

    String className = publicClassNames
      .stream()
      .findFirst()
      .orElse("");

    JavaFileObject file = new SimpleJavaFileObject(
      URI.create("string:///" + className + JavaFileObject.Kind.SOURCE.extension),
      JavaFileObject.Kind.SOURCE) {
      @Override
      public CharSequence getCharContent(boolean ignoreEncodingErrors) throws IOException {
        return source;
      }
    };

    Iterable<JavaFileObject> compilationUnits = Arrays.asList(file);

    JavaCompiler.CompilationTask task = compiler.getTask(
      null,
      null,
      diagnostics,
      null,
      null,
      compilationUnits);

    boolean success = task.call();

    if (success) {
      try {
        URLClassLoader classLoader = new URLClassLoader(new URL[] {
          new File("./")
            .toURI()
            .toURL()
        });

        List<Object> inputs = arguments
          .stream()
          .map(argument -> {
            try {
              return objectMapper.treeToValue(argument, Object.class);
            } catch (JsonProcessingException exception) {
              throw new IllegalArgumentException(exception.getMessage());
            }
          })
          .collect(Collectors.toList());

        List<Class> methodSignature = inputs
          .stream()
          .map(object -> object.getClass())
          .collect(Collectors.toList());

        Class mainClass = Class.forName(className, true, classLoader);

        Method method = Arrays
          .stream(mainClass.getDeclaredMethods())
          .filter(m -> m.getName().equals("compute"))
          .filter(m -> m.getParameterCount() == inputs.size())
          .filter(m -> IntStream
            .range(0, m.getParameterCount())
            .allMatch(i -> m
              .getParameterTypes()[i]
              .isAssignableFrom(methodSignature.get(i))))
          .findFirst()
          .orElseThrow(() -> new NoSuchMethodException(
            String.format(
              "%s.%s(%s)",
              className,
              "compute",
              methodSignature
                .stream()
                .map(Class::getName)
                .collect(Collectors.joining(", ")))
          ));

        ByteArrayOutputStream logStream = new ByteArrayOutputStream();
        System.setOut(new PrintStream(logStream));

        Object output;

        try {
          output = method.invoke(null, inputs.toArray());
        }
        finally {
          System.setOut(standardOutput);
        }

        return Tuples.of(objectMapper.valueToTree(output), logStream.toString(), 0);
      } catch (Exception exception) {
        StringWriter logWriter = new StringWriter();
        exception.printStackTrace(new PrintWriter(logWriter));

        return Tuples.of(objectMapper.nullNode(), logWriter.toString(), 1);
      }
    }
    else {
      StringBuilder logBuilder = new StringBuilder();

      diagnostics
        .getDiagnostics()
        .forEach(diagnostic -> logBuilder
          .append(diagnostic.toString())
          .append('\n'));

      return Tuples.of(objectMapper.nullNode(), logBuilder.toString(), 1);
    }
  }

  public static void main(String[] args) throws JsonProcessingException {
    ObjectMapper objectMapper = new ObjectMapper();
    System.out.println(Application.execute(
      "import java.util.List;\n" +
      "\n" +
      "public class Lambda {\n" +
      "  public static Number compute(List<Number> numbers) {\n" +
      "    System.out.println(\"HelloWorld\");" +
      "    \n" +
      "    return numbers\n" +
      "      .stream()\n" +
      "      .reduce(0, (a, b) -> a.doubleValue() + b.doubleValue());\n" +
      "  }\n" +
      "}",
      Arrays.asList(
        objectMapper.readTree("1"),
        objectMapper.readTree("[0, 1.0, 2, 3, 4, 5]")
      )));
  }
}
