package net.lenords.yama.model.events;

public interface EventListener extends Runnable {

  String getName();

  @Override
  void run();

}
