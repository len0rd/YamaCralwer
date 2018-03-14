package net.lenords.yama.model.actions;

public interface Action<T> {

  ActionResult performAction(T context);

}
