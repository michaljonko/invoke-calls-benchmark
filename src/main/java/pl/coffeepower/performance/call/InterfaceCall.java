package pl.coffeepower.performance.call;

import pl.coffeepower.performance.SimpleValueProvider;
import pl.coffeepower.performance.ValueProvider;

public final class InterfaceCall extends AbstractCall {

  private final ValueProvider provider = new SimpleValueProvider();

  @Override
  public long call() {
    return provider.longValue();
  }
}
