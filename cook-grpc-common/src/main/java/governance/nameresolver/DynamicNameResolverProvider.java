package governance.nameresolver;

import io.grpc.NameResolver;
import io.grpc.NameResolverProvider;

import java.net.URI;

public class DynamicNameResolverProvider extends NameResolverProvider {

  @Override
  public NameResolver newNameResolver(URI targetUri, NameResolver.Args args) {
    if (targetUri.getScheme().equals("zookeeper")) {
      return new ZKNameResolver(targetUri.getHost());
    } else {
      throw new RuntimeException("unknown name resolver schema " + targetUri.getScheme());
    }
  }

  @Override
  protected boolean isAvailable() {
    return true;
  }

  @Override
  protected int priority() {
    return 10;
  }

  @Override
  public String getDefaultScheme() {
    return "dynamic";
  }
}
