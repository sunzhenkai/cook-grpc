package governance.stub;

import governance.model.StubWrapper;
import governance.nameresolver.DynamicNameResolverProvider;
import io.grpc.Channel;
import io.grpc.ClientInterceptor;
import io.grpc.ManagedChannelBuilder;
import io.grpc.NameResolverRegistry;
import io.grpc.stub.AbstractStub;
import lombok.SneakyThrows;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@SuppressWarnings({"unchecked", "JavaReflectionInvocation"})
public class StubFactory {
  private static final Map<StubWrapper, AbstractStub<?>> channels = new ConcurrentHashMap<>();

  public static <IStub> IStub getStub(Class<IStub> cls, String name) {
    return (IStub) getStub(cls, name, new Class[] {});
  }

  public static <IStub> IStub getStub(Class<IStub> cls, String name,
      List<? extends ClientInterceptor> interceptors) {
    return getStub(cls, name, (Class<? extends ClientInterceptor>[]) interceptors.toArray());
  }

  public static <IStub> IStub getStub(Class<IStub> cls, String name,
      Class<? extends ClientInterceptor>[] interceptors) {
    StubWrapper sw = new StubWrapper();
    sw.setCls(cls);
    sw.setName(name);
    sw.setInterceptors(interceptors);
    return (IStub) channels.computeIfAbsent(sw, StubFactory::createStub);
  }

  @SneakyThrows
  private static <IStub extends AbstractStub<IStub>> IStub createStub(StubWrapper sw) {
    NameResolverRegistry.getDefaultRegistry().register(new DynamicNameResolverProvider());
    ManagedChannelBuilder<?> builder = ManagedChannelBuilder
        .forTarget("zookeeper://" + sw.getName())  // FIXME hard code for zookeeper
        .usePlaintext();

    if (sw.getInterceptors() != null && sw.getInterceptors().size() > 0) {
      for (int i = 0; i < sw.getInterceptors().size(); ++i) {
        Class<? extends ClientInterceptor> it = sw.getInterceptors().get(i);
        builder.intercept(it.newInstance());
      }
    }
    Channel channel = builder.build();

    String stubClsName = sw.getCls().getName();
    String grpcClsName = sw.getCls().getName().substring(0, stubClsName.lastIndexOf("$"));
    Class<?> grpcCls = sw.getCls().getClassLoader().loadClass(grpcClsName);
    // FIXME create blocking or non blocking according to stub class name
    return (IStub) grpcCls.getMethod("newBlockingStub").invoke(null, channel);
  }
}
