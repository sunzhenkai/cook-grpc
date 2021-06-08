package governance.discovery;

import governance.model.InstanceDetail;
import lombok.SneakyThrows;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.utils.CloseableUtils;
import org.apache.curator.x.discovery.*;
import org.apache.curator.x.discovery.details.JsonInstanceSerializer;
import org.apache.curator.x.discovery.details.ServiceCacheListener;

import java.io.Closeable;
import java.util.Collection;

import static governance.constants.Constants.BASE_PATH;

public class Discovery<T> implements Closeable {
    private final ServiceDiscovery<T> discovery;
    private ServiceProvider<T> provider;
    private ServiceCache<T> cache;
    private final InstanceDetail<T> instance;
    private final ServiceInstance<T> service;

    public Discovery(CuratorFramework cf, Class<T> cls, InstanceDetail<T> instance) throws Exception {
        this.instance = instance;
        this.service = toServiceInstance(instance);
        JsonInstanceSerializer<T> serializer = new JsonInstanceSerializer<>(cls);
        discovery = ServiceDiscoveryBuilder.builder(cls)
                .client(cf)
                .basePath(BASE_PATH)
                .serializer(serializer)
                .build();
        discovery.start();
    }

    private ServiceProvider<T> buildProvider() throws Exception {
        ServiceProvider<T> pvd = discovery.serviceProviderBuilder()
                .serviceName(this.instance.getName())
                .build();
        pvd.start();
        return pvd;
    }

    private ServiceProvider<T> getProvider() throws Exception {
        if (this.provider == null) {
            synchronized (this) {
                this.provider = buildProvider();
            }
        }
        return provider;
    }

    public void register() throws Exception {
        discovery.registerService(this.service);
    }

    @SneakyThrows
    public void unregister() {
        discovery.unregisterService(this.service);
    }

    public ServiceInstance<T> random() throws Exception {
        return getProvider().getInstance();
    }

    public Collection<ServiceInstance<T>> all() throws Exception {
        return getProvider().getAllInstances();
    }

    public ServiceCache<T> buildCache() throws Exception {
        ServiceCache<T> cc = discovery.serviceCacheBuilder().name(this.instance.getName()).build();
        cc.start();
        return cc;
    }

    public ServiceCache<T> getCache() throws Exception {
        if (cache == null) {
            synchronized (this) {
                this.cache = buildCache();
            }
        }
        return cache;
    }

    public void listen(ServiceCacheListener listener) throws Exception {
        getCache().addListener(listener);
    }

    @Override
    public void close() {
        CloseableUtils.closeQuietly(discovery);
    }

    public Collection<String> queryForNames() throws Exception {
        return discovery.queryForNames();
    }

    @SneakyThrows
    private static <T> ServiceInstance<T> toServiceInstance(InstanceDetail<T> instance) {
        return ServiceInstance.<T>builder()
                .port(instance.getPort())
                .payload(instance.getPayload())
                .address(instance.getHost())
                .name(instance.getName())
                .id(instance.getId())
                .build();
    }
}
