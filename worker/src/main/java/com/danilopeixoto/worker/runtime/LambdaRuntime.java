package com.danilopeixoto.worker.runtime;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ArrayNode;
import reactor.util.function.Tuple3;

public interface LambdaRuntime {
  static String Entrypoint = "compute";

  Tuple3<JsonNode, String, Integer> execute(final String source, final ArrayNode arguments);
}
