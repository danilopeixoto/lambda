package com.danilopeixoto.worker.runtime.java;

import com.danilopeixoto.worker.runtime.LambdaRuntime;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.github.javaparser.StaticJavaParser;
import com.github.javaparser.ast.CompilationUnit;
import reactor.util.function.Tuple3;
import reactor.util.function.Tuples;

import javax.tools.*;
import java.io.*;
import java.lang.reflect.Method;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class JavaLambdaRuntime implements LambdaRuntime {
  private final ObjectMapper jsonMapper;
  private final PrintStream standardOutput;
  private final MainClassNameCollector mainClassNameCollector;
  private final JavaCompiler compiler;
  private final DiagnosticCollector<JavaFileObject> diagnostics;

  public JavaLambdaRuntime() {
    this.jsonMapper = new ObjectMapper();
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
    CompilationUnit compilationUnit = StaticJavaParser.parse(source);
    List<String> classNames = new ArrayList<>();

    this.mainClassNameCollector.visit(compilationUnit, classNames);

    return classNames
      .stream()
      .findFirst()
      .orElse("");
  }

  private List<Object> argumentToInputs(final ArrayNode arguments) throws JsonProcessingException {
    return List.of(this.jsonMapper.treeToValue(arguments, Object[].class));
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
    final List<Class<?>> methodSignature) throws ClassNotFoundException, NoSuchMethodException {
    return Arrays
      .stream(Class
        .forName(className, true, classLoader)
        .getDeclaredMethods())
      .filter(method -> method.getName().equals(LambdaRuntime.Entrypoint))
      .filter(method -> method.getParameterCount() == methodSignature.size())
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
          LambdaRuntime.Entrypoint,
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
      List.of(file));
  }

  @Override
  public Tuple3<JsonNode, String, Integer> execute(final String source, final ArrayNode arguments) {
    try {
      ClassLoader classLoader = this.getClassLoader();
      String className = this.getMainClassName(source);
      JavaFileObject file = this.buildFile(className, source);
      JavaCompiler.CompilationTask task = this.compile(file);

      if (task.call()) {
        List<Object> inputs = this.argumentToInputs(arguments);
        List<Class<?>> methodSignature = this.getMethodSignature(inputs);
        Method method = this.getEntrypointMethod(classLoader, className, methodSignature);

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

        throw new IllegalArgumentException(logBuilder.toString());
      }
    } catch (Exception exception) {
      StringWriter logWriter = new StringWriter();
      exception.printStackTrace(new PrintWriter(logWriter));

      return Tuples.of(this.jsonMapper.nullNode(), logWriter.toString(), 1);
    }
  }
}
