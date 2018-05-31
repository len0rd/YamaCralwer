package net.lenords.yama.model.actions;

public interface Action<T, R extends ActionResult> {

  R run(T context);

  String getName();
}
