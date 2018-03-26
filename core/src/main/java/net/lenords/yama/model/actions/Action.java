package net.lenords.yama.model.actions;

public interface Action<T, R extends ActionResult> {

  ActionResult run(T context);

  String getName();

}
