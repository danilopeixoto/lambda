package com.danilopeixoto.worker.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public enum StatusType {
  @JsonProperty("ready")
  Ready,

  @JsonProperty("done")
  Done,

  @JsonProperty("error")
  Error
}
