package net.lenords.yama.internal.model;

public interface Tuple<T, V> {


  T getKey();

  void setKey(T key);

  V getValue();

  void setValue(V value);

  boolean hasValue();
}
