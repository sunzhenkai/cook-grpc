package pub.wii.cook.gapic.client;

import io.grpc.EquivalentAddressGroup;
import io.grpc.NameResolver;

import java.net.SocketAddress;
import java.net.URI;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * TODO
 *
 * @author wii
 * @version 1.0.0
 * @since 2021/06/03 17:46
 */
@Deprecated
public class MultiAddressNameResolverFactory extends NameResolver.Factory {
    final List<EquivalentAddressGroup> addresses;

    public MultiAddressNameResolverFactory(SocketAddress... addresses) {
        this.addresses = Arrays.stream(addresses)
                .map(EquivalentAddressGroup::new)
                .collect(Collectors.toList());
    }

    @Override
    public NameResolver newNameResolver(URI targetUri, NameResolver.Args args) {
        NameResolver nr = new NameResolver() {
            @Override
            public String getServiceAuthority() {
                return "fake";
            }

            @Override
            public void start(Listener2 listener) {
                super.start(listener);
            }

            @Override
            public void start(Listener listener) {
                super.start(listener);
            }

            @Override
            public void refresh() {
                super.refresh();
            }

            @Override
            public void shutdown() {

            }
        };
        nr.refresh();
        return nr;
    }

    @Override
    public String getDefaultScheme() {
        return "multiaddress";
    }
}
