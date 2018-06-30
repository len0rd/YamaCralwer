package net.lenords.yama.internal.model.actions;

public interface ActionResult<T> {

  void setResult(T result);

  T getResult();

  boolean isEmpty();

}
