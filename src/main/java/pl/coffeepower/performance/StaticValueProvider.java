package pl.coffeepower.performance;

import java.lang.invoke.CallSite;
import java.lang.invoke.ConstantCallSite;
import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;

public final class StaticValueProvider {

  private StaticValueProvider() {
    throw new UnsupportedOperationException();
  }

  public static long longValue() {
    return 1L;
  }

  public static CallSite bootstrap(Object... args) throws Throwable {
    MethodHandle methodHandle = MethodHandles.lookup()
        .findStatic(StaticValueProvider.class, "longValue", MethodType.methodType(long.class));
    return new ConstantCallSite(methodHandle);
  }
}
