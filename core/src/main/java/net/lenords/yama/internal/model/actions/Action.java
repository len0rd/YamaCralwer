package net.lenords.yama.internal.model.actions;

public interface Action<T, R extends ActionResult> {

	R run(T context);

	String getName();
}
