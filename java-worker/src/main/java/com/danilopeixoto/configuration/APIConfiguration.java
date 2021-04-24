package com.danilopeixoto.configuration;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotEmpty;

@Validated
@Configuration
@ConfigurationProperties(prefix = "api")
public class APIConfiguration {
  @NotEmpty
  private String hostname;

  @NotEmpty
  private String port;

  @NotEmpty
  private String version;

  @NotEmpty
  private String javaWorkerTopicName;

  public APIConfiguration(
    String hostname,
    String port,
    String version,
    String javaWorkerTopicName) {
    this.hostname = hostname;
    this.port = port;
    this.version = version;
    this.javaWorkerTopicName = javaWorkerTopicName;
  }

  public void setHostname(String hostname) {
    this.hostname = hostname;
  }

  public void setPort(String port) {
    this.port = port;
  }

  public void setVersion(String version) {
    this.version = version;
  }

  public void setJavaWorkerTopicName(String javaWorkerTopicName) {
    this.javaWorkerTopicName = javaWorkerTopicName;
  }

  public String getHostname() {
    return this.hostname;
  }

  public String getPort() {
    return this.port;
  }

  public String getVersion() {
    return this.version;
  }

  public String getJavaWorkerTopicName() {
    return this.javaWorkerTopicName;
  }
}
