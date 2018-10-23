package pl.coffeepower.performance.call;

import pl.coffeepower.performance.ValueProvider;

public final class LambdaCall extends AbstractCall {

  private final ValueProvider provider = ((ValueProvider) () -> 2L);

  @Override
  public long call() {
    return provider.longValue();
  }
}
