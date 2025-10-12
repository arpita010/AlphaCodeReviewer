package com.app.config;

import com.app.constants.KafkaConstants;
import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;

@Configuration
public class KafkaConfig {
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
}
