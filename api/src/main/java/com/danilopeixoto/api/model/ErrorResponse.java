package com.danilopeixoto.api.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import javax.validation.constraints.NotEmpty;

public class ErrorResponse {
  @NotEmpty(message = "The message field is required.")
  @JsonProperty("message")
  private String message;

  public ErrorResponse() {
  }

  public ErrorResponse(String message) {
    this.message = message;
  }

  public void setMessage(String message) {
    this.message = message;
  }

  public String getMessage() {
    return this.message;
  }
}
