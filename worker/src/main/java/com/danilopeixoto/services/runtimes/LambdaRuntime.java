package com.danilopeixoto.services.runtimes;

import com.fasterxml.jackson.databind.JsonNode;
import reactor.util.function.Tuple3;

import java.util.List;

public interface LambdaRuntime {
  static String EntrypointMethodName = "compute";

  Tuple3<JsonNode, String, Integer> execute(final String source, final List<JsonNode> arguments);
}
