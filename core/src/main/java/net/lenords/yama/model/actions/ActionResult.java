package net.lenords.yama.model.actions;

public interface ActionResult<T> {

  void setResult(T result);

  T getResult();

}
