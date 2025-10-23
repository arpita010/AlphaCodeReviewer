package com.app.constants;

public enum ReviewSide {
  LEFT("LEFT"),
  RIGHT("RIGHT");
  private String side;

  ReviewSide(String side) {
    this.side = side;
  }

  public String getSide() {
    return side;
  }
}
