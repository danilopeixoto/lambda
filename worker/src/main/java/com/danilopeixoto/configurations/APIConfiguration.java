package com.danilopeixoto.configurations;

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
  private String topicName;

  public APIConfiguration(
    String hostname,
    String port,
    String version,
    String topicName) {
    this.hostname = hostname;
    this.port = port;
    this.version = version;
    this.topicName = topicName;
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

  public void setTopicName(String topicName) {
    this.topicName = topicName;
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

  public String getTopicName() {
    return this.topicName;
  }
}
