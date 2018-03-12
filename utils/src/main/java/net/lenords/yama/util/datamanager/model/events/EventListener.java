package net.lenords.yama.util.datamanager.model.events;

public abstract class EventListener<T> {

  public abstract String getName();

  public abstract void run(T contextInfo);

}
