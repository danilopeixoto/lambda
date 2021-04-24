package com.danilopeixoto.configurations;

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
  private String topicName;

  @Min(1)
  private int topicPartitions;

  public APIConfiguration(
    String version,
    String topicName,
    int topicPartitions) {
    this.version = version;
    this.topicName = topicName;
    this.topicPartitions = topicPartitions;
  }

  public void setVersion(String version) {
    this.version = version;
  }

  public void setTopicName(String topicName) {
    this.topicName = topicName;
  }

  public void setTopicPartitions(int topicPartitions) {
    this.topicPartitions = topicPartitions;
  }

  public String getVersion() {
    return this.version;
  }

  public String getTopicName() {
    return this.topicName;
  }

  public int getTopicPartitions() {
    return this.topicPartitions;
  }
}
