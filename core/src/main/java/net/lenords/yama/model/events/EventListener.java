package net.lenords.yama.model.events;

public abstract class EventListener<T> {

  public abstract String getName();

  public abstract void run(T contextInfo);

}
