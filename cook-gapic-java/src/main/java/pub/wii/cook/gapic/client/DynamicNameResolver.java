package pub.wii.cook.gapic.client;

import com.google.common.collect.Lists;
import io.grpc.EquivalentAddressGroup;
import io.grpc.NameResolver;

import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

/**
 * TODO
 *
 * @author wii
 * @version 1.0.0
 * @since 2021/06/03 23:04
 */
public class DynamicNameResolver extends NameResolver {
    private final ScheduledExecutorService executorService = new ScheduledThreadPoolExecutor(10);

    private String authority;
    private Listener2 listener;

    public DynamicNameResolver(String authority) {
        this.authority = authority;
        executorService.scheduleAtFixedRate(this::resolve, 5, 10, TimeUnit.SECONDS);
    }

    @Override
    public String getServiceAuthority() {
        return this.authority;
    }

    @Override
    public void shutdown() {
    }

    @Override
    public void start(Listener2 listener) {
        this.listener = listener;
        this.resolve();
    }

    @Override
    public void refresh() {
        resolve();
    }

    void resolve() {
        List<InetSocketAddress> addressList = getAddressList(this.authority);
        List<EquivalentAddressGroup> eas = addressList.stream()
                .map(this::toSocketAddress)
                .map(Lists::newArrayList)
                .map(EquivalentAddressGroup::new)
                .collect(Collectors.toList());

        Collections.shuffle(eas); // for test
        System.out.println("shuffle result: " + eas.get(0));
        ResolutionResult resolutionResult = ResolutionResult.newBuilder()
                .setAddresses(Collections.singletonList(eas.get(0)))
                .build();

        listener.onResult(resolutionResult);
    }

    private SocketAddress toSocketAddress(InetSocketAddress address) {
        return new InetSocketAddress(address.getHostName(), address.getPort());
    }

    private List<InetSocketAddress> getAddressList(String authority) {
        return Arrays.asList(
                new InetSocketAddress("127.0.0.1", 9900),
                new InetSocketAddress("172.20.10.228", 9901),
                new InetSocketAddress("localhost", 9902)
        );
    }
}
