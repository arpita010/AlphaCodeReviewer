package com.app.constants;

public enum ConverterType {
  CREATE_REVIEW_COMMENT_DTO_CONVERTER("CREATE_REVIEW_COMMENT_DTO_CONVERTER"),
  CUSTOM_REVIEW_RESPONSE_CONVERTER("CUSTOM_REVIEW_RESPONSE_CONVERTER");
  private String type;

  ConverterType(String type) {
    this.type = type;
  }

  public String type() {
    return this.type;
  }
}
