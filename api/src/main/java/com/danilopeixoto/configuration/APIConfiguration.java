package com.danilopeixoto.configuration;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;

@Validated
@Configuration
@ConfigurationProperties(prefix = "api")
public class APIConfiguration {
  @NotEmpty
  private String version;

  @NotEmpty
  private String javaWorkerTopicName;

  @Min(1)
  private int partitionsPerTopic;

  public APIConfiguration(
    String version,
    String javaWorkerTopicName,
    int partitionsPerTopic) {
    this.version = version;
    this.javaWorkerTopicName = javaWorkerTopicName;
    this.partitionsPerTopic = partitionsPerTopic;
  }

  public void setVersion(String version) {
    this.version = version;
  }

  public void setJavaWorkerTopicName(String javaWorkerTopicName) {
    this.javaWorkerTopicName = javaWorkerTopicName;
  }

  public void setPartitionsPerTopic(int partitionsPerTopic) {
    this.partitionsPerTopic = partitionsPerTopic;
  }

  public String getVersion() {
    return version;
  }

  public String getJavaWorkerTopicName() {
    return javaWorkerTopicName;
  }

  public int getPartitionsPerTopic() {
    return partitionsPerTopic;
  }
}
