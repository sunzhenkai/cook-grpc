package governance.nameresolver;

import com.google.common.collect.Lists;
import governance.discovery.Discovery;
import governance.model.InstanceDetail;
import governance.model.Payload;
import governance.utils.ZKUtils;
import io.grpc.EquivalentAddressGroup;
import io.grpc.NameResolver;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.state.ConnectionState;
import org.apache.curator.x.discovery.ServiceInstance;
import org.apache.curator.x.discovery.details.ServiceCacheListener;

import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
public class ZKNameResolver extends NameResolver implements ServiceCacheListener {
    private final String name;
    private Listener2 listener;
    private final InstanceDetail<Payload> instance;
    private final Discovery<Payload> discovery;

    @SneakyThrows
    public ZKNameResolver(String name) {
        this.name = name;
        this.instance = new InstanceDetail<>();
        this.init();
        this.discovery = new Discovery<>(ZKUtils.getLocalCuratorFramework(), Payload.class, this.instance);
        this.discovery.listen(this);
    }

    @SneakyThrows
    private void init() {
        this.instance.setName(this.name);
        this.instance.setHost(InetAddress.getLocalHost().getCanonicalHostName());
        this.instance.setPort(9040);
    }

    @Override
    public void start(Listener2 listener) {
        this.listener = listener;
        this.resolve();
    }

    @Override
    public void refresh() {
        this.resolve();
    }

    @SneakyThrows
    private void resolve() {
        Collection<ServiceInstance<Payload>> instances = this.discovery.all();
        List<EquivalentAddressGroup> eas = instances.stream()
                .map(this::toSocketAddress)
                .map(Lists::newArrayList)
                .map(EquivalentAddressGroup::new)
                .collect(Collectors.toList());

        ResolutionResult rr = ResolutionResult.newBuilder()
                .setAddresses(Collections.singletonList(eas.get(0)))
                .build();
        listener.onResult(rr);
        log.info("refresh backends list successfully with instances {}", instances);
    }

    private SocketAddress toSocketAddress(ServiceInstance<Payload> instance) {
        return new InetSocketAddress(instance.getAddress(), instance.getPort());
    }

    @Override
    public String getServiceAuthority() {
        return this.name;
    }

    @SneakyThrows
    @Override
    public void shutdown() {
    }

    @Override
    public void cacheChanged() {
        this.resolve();
    }

    @Override
    public void stateChanged(CuratorFramework curatorFramework, ConnectionState connectionState) {

    }
}
