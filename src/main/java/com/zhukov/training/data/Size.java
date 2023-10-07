package com.zhukov.training.data;

public enum Size {
  SMALL("small");

  private final String value;

  Size(String value) {
    this.value = value;
  }

  public String getValue() {
    return value;
  }
}
