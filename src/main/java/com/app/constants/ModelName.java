package com.app.constants;

public enum ModelName {
  OLLAMA("OLLAMA"),
  BEDROCK("BEDROCK"),
  GEMINI("GEMINI");

  private String name;

  ModelName(String name) {
    this.name = name;
  }

  public String getName() {
    return this.name;
  }
}
