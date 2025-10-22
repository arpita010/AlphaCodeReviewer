package com.app.constants;

public enum ApiResponse {
  SUCCESS("SUCCESS"),
  FAILED("FAILED");
  private String status;

  ApiResponse(String status) {
    this.status = status;
  }

  public String toString() {
    return this.status;
  }
}
