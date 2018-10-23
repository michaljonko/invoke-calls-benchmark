package pl.coffeepower.performance;

import java.util.concurrent.TimeUnit;
import net.bytebuddy.ByteBuddy;
import net.bytebuddy.dynamic.loading.ClassLoadingStrategy;
import net.bytebuddy.implementation.InvokeDynamic;
import net.bytebuddy.matcher.ElementMatchers;
import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.BenchmarkMode;
import org.openjdk.jmh.annotations.Fork;
import org.openjdk.jmh.annotations.Measurement;
import org.openjdk.jmh.annotations.Mode;
import org.openjdk.jmh.annotations.OutputTimeUnit;
import org.openjdk.jmh.annotations.Scope;
import org.openjdk.jmh.annotations.State;
import org.openjdk.jmh.annotations.Threads;
import org.openjdk.jmh.annotations.Warmup;
import pl.coffeepower.performance.call.AbstractCall;
import pl.coffeepower.performance.call.InterfaceCall;
import pl.coffeepower.performance.call.LambdaCall;
import pl.coffeepower.performance.call.StaticCall;
import pl.coffeepower.performance.call.VirtualCall;

@BenchmarkMode({Mode.Throughput, Mode.AverageTime})
@OutputTimeUnit(TimeUnit.NANOSECONDS)
@Threads(2)
@Fork(1)
@Measurement(time = 1, timeUnit = TimeUnit.SECONDS, iterations = 5)
@Warmup(time = 1, timeUnit = TimeUnit.SECONDS, iterations = 5)
public class InvokeBenchmarks {

  @Benchmark
  public boolean measureInvokeInterface(Implementations impl) {
    return impl.interfaceCall.call() == 0L;
  }

  @Benchmark
  public boolean measureInvokeVirtual(Implementations impl) {
    return impl.virtualCall.call() == 0L;
  }

  @Benchmark
  public boolean measureInvokeStatic(Implementations impl) {
    return impl.staticCall.call() == 1L;
  }

  @Benchmark
  public boolean measureInvokeDynamic(Implementations impl) {
    return impl.dynamicCall.call() == 1L;
  }

  @Benchmark
  public boolean measureInvokeLambda(Implementations impl) {
    return impl.lambdaCall.call() == 2L;
  }

  @State(Scope.Benchmark)
  public static class Implementations {

    final InterfaceCall interfaceCall;
    final VirtualCall virtualCall;
    final StaticCall staticCall;
    final AbstractCall dynamicCall;
    final LambdaCall lambdaCall;

    public Implementations() {
      this.interfaceCall = new InterfaceCall();
      this.virtualCall = new VirtualCall();
      this.staticCall = new StaticCall();
      this.lambdaCall = new LambdaCall();
      try {
        this.dynamicCall = new ByteBuddy()
            .subclass(AbstractCall.class)
            .method(ElementMatchers.named("call"))
            .intercept(InvokeDynamic
                .bootstrap(StaticValueProvider.class.getDeclaredMethod("bootstrap", Object[].class))
                .withoutArguments())
            .make()
            .load(AbstractCall.class.getClassLoader(), ClassLoadingStrategy.Default.INJECTION)
            .getLoaded()
            .newInstance();
      } catch (InstantiationException | IllegalAccessException | NoSuchMethodException e) {
        throw new java.lang.IllegalStateException(e);
      }
    }
  }

}
