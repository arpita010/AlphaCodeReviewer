package com.app.config;

import com.app.constants.KafkaConstants;
import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;

@Configuration
public class KafkaConfig {
  private static final int PARTITIONS = 3;

  @Bean
  public NewTopic topic() {
    return TopicBuilder.name("my-first-topic")
        //                .partitions(2)
        .build();
  }

  @Bean
  public NewTopic createPullRequestEditTopic() {
    return TopicBuilder.name(KafkaConstants.PR_EDIT_TOPIC_NAME).build();
  }

  @Bean
  public NewTopic createPullRequestTopic() {
    return TopicBuilder.name(KafkaConstants.PULL_REQUEST).partitions(PARTITIONS).build();
  }

  @Bean
  public NewTopic createReviewCommentTopic() {
    return TopicBuilder.name(KafkaConstants.CREATE_REVIEW_COMMENT).partitions(PARTITIONS).build();
  }
}
