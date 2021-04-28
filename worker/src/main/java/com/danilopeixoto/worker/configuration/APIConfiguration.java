package com.danilopeixoto.worker.configuration;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotEmpty;

@Validated
@ConfigurationProperties(prefix = "api")
@Configuration
public class APIConfiguration {
  @NotEmpty
  private String hostname;

  @NotEmpty
  private String port;

  @NotEmpty
  private String version;

  @NotEmpty
  private String topicName;

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
