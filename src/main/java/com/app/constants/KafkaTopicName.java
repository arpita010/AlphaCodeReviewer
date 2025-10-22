package com.app.constants;

public enum KafkaTopicName {
  PULL_REQUEST("PULL_REQUEST"),
  CREATE_REVIEW_COMMENT("CREATE_REVIEW_COMMENT");

  private String topicName;

  KafkaTopicName(String topicName) {
    this.topicName = topicName;
  }

  public String toString() {
    return this.topicName;
  }
}
