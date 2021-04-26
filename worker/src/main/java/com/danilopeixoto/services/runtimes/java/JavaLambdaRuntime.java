package com.danilopeixoto.services.runtimes.java;

import com.danilopeixoto.services.runtimes.LambdaRuntime;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.javaparser.StaticJavaParser;
import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.visitor.VoidVisitor;
import org.apache.logging.log4j.util.Strings;
import org.springframework.beans.factory.annotation.Autowired;
import reactor.util.function.Tuple3;
import reactor.util.function.Tuples;

import javax.tools.*;
import java.io.*;
import java.lang.reflect.Method;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class JavaLambdaRuntime implements LambdaRuntime {
  @Autowired
  private ObjectMapper jsonMapper;

  private final PrintStream standardOutput;
  private final VoidVisitor<String> mainClassNameCollector;
  private final JavaCompiler compiler;
  private final DiagnosticCollector<JavaFileObject> diagnostics;

  public JavaLambdaRuntime() {
    this.standardOutput = System.out;
    this.mainClassNameCollector = new MainClassNameCollector();
    this.compiler = ToolProvider.getSystemJavaCompiler();
    this.diagnostics = new DiagnosticCollector<>();
  }

  private ClassLoader getClassLoader() throws MalformedURLException {
    return new URLClassLoader(new URL[]{
      new File("./")
        .toURI()
        .toURL()
    });
  }

  private String getMainClassName(final String source) {
    String className = Strings.EMPTY;
    CompilationUnit compilationUnit = StaticJavaParser.parse(source);

    this.mainClassNameCollector.visit(compilationUnit, className);

    return className;
  }

  private List<Object> argumentToInputs(final List<JsonNode> arguments) throws IllegalArgumentException {
    return arguments
      .stream()
      .map(argument -> {
        try {
          return this.jsonMapper.treeToValue(argument, Object.class);
        } catch (JsonProcessingException exception) {
          throw new IllegalArgumentException(exception.getMessage());
        }
      })
      .collect(Collectors.toList());
  }

  private List<Class<?>> getMethodSignature(final List<Object> inputs) {
    return inputs
      .stream()
      .map(Object::getClass)
      .collect(Collectors.toList());
  }

  private Method getEntrypointMethod(
    final ClassLoader classLoader,
    final String className,
    final List<Class<?>> methodSignature,
    final List<Object> inputs) throws ClassNotFoundException, NoSuchMethodException {
    return Arrays
      .stream(Class
        .forName(className, true, classLoader)
        .getDeclaredMethods())
      .filter(method -> method.getName().equals(LambdaRuntime.EntrypointMethodName))
      .filter(method -> method.getParameterCount() == inputs.size())
      .filter(method -> IntStream
        .range(0, method.getParameterCount())
        .allMatch(i -> method
          .getParameterTypes()[i]
          .isAssignableFrom(methodSignature.get(i))))
      .findFirst()
      .orElseThrow(() -> new NoSuchMethodException(
        String.format(
          "%s.%s(%s)",
          className,
          LambdaRuntime.EntrypointMethodName,
          methodSignature
            .stream()
            .map(Class::getName)
            .collect(Collectors.joining(", ")))
      ));
  }

  private JavaFileObject buildFile(final String className, final String source) {
    return new SimpleJavaFileObject(
      URI.create("string:///" + className + JavaFileObject.Kind.SOURCE.extension),
      JavaFileObject.Kind.SOURCE) {
      @Override
      public CharSequence getCharContent(boolean ignoreEncodingErrors) throws IOException {
        return source;
      }
    };
  }

  private JavaCompiler.CompilationTask compile(final JavaFileObject file) {
    return compiler.getTask(
      null,
      null,
      this.diagnostics,
      null,
      null,
      Arrays.asList(file));
  }

  @Override
  public Tuple3<JsonNode, String, Integer> execute(final String source, final List<JsonNode> arguments) {
    try {
      ClassLoader classLoader = this.getClassLoader();
      String className = this.getMainClassName(source);
      JavaFileObject file = this.buildFile(className, source);
      JavaCompiler.CompilationTask task = this.compile(file);

      if (task.call()) {
        List<Object> inputs = this.argumentToInputs(arguments);
        List<Class<?>> methodSignature = this.getMethodSignature(inputs);
        Method method = this.getEntrypointMethod(classLoader, className, methodSignature, inputs);

        ByteArrayOutputStream logStream = new ByteArrayOutputStream();
        System.setOut(new PrintStream(logStream));

        Object output;

        try {
          output = method.invoke(null, inputs.toArray());
        } finally {
          System.setOut(this.standardOutput);
        }

        return Tuples.of(this.jsonMapper.valueToTree(output), logStream.toString(), 0);
      } else {
        StringBuilder logBuilder = new StringBuilder();

        this.diagnostics
          .getDiagnostics()
          .forEach(diagnostic -> logBuilder
            .append(diagnostic.toString())
            .append('\n'));

        throw new RuntimeException(logBuilder.toString());
      }
    } catch (Exception exception) {
      StringWriter logWriter = new StringWriter();
      exception.printStackTrace(new PrintWriter(logWriter));

      return Tuples.of(this.jsonMapper.missingNode(), logWriter.toString(), 1);
    }
  }
}
