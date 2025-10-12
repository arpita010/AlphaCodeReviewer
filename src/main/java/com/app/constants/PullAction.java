package com.app.constants;

public enum PullAction {
  CREATED("CREATED"),
  EDITED("EDITED"),
  UPDATED("UPDATED");

  private String action;

  PullAction(String action) {
    this.action = action;
  }

  public String toString() {
    return action;
  }
}
