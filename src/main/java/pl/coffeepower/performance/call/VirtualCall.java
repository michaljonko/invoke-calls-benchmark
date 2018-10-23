package pl.coffeepower.performance.call;

import pl.coffeepower.performance.SimpleValueProvider;

public final class VirtualCall extends AbstractCall {

  private final SimpleValueProvider provider = new SimpleValueProvider();

  @Override
  public long call() {
    return provider.longValue();
  }
}
