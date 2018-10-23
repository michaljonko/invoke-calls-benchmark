package pl.coffeepower.performance.call;

import pl.coffeepower.performance.StaticValueProvider;

public final class StaticCall extends AbstractCall {

  @Override
  public long call() {
    return StaticValueProvider.longValue();
  }
}
