package pub.wii.cook.gapic.client;

import io.grpc.NameResolver;
import io.grpc.NameResolverProvider;

import java.net.URI;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledThreadPoolExecutor;

/**
 * TODO
 *
 * @author wii
 * @version 1.0.0
 * @since 2021/06/03 23:03
 */
public class DynamicNameResolverProvider extends NameResolverProvider {
    @Override
    protected boolean isAvailable() {
        return true;
    }

    @Override
    protected int priority() {
        return 10;
    }

    @Override
    public NameResolver newNameResolver(URI targetUri, NameResolver.Args args) {
        return new DynamicNameResolver(targetUri.toString());
    }

    @Override
    public String getDefaultScheme() {
        return "http";
    }
}
