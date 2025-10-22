package com.app.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class BeanConfigurer {

  @Bean
  public ObjectMapper objectMapper() {
    return new ObjectMapper();
  }
}
