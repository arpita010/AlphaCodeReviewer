package com.app.utils;

public interface Converter<T> {
  String serialize(T message);

  T deserialize(String message);
}
