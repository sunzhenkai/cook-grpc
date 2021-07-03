package pub.wii.cook.gapic.client;

import io.grpc.*;
import pub.wii.cook.v1.CookServiceGrpc;
import pub.wii.cook.v1.PingRequest;

public class CookServiceClientMultiple {

    private final CookServiceGrpc.CookServiceBlockingStub bs;

    public CookServiceClientMultiple(Channel channel) {
        this.bs = CookServiceGrpc.newBlockingStub(channel);
    }

    public String ping() {
        return this.bs.ping(PingRequest.newBuilder().setMsg("ping").build()).getRes();
    }

    public static void main(String[] args) throws InterruptedException {
        NameResolverRegistry.getDefaultRegistry().register(new DynamicNameResolverProvider());
        ManagedChannel channel = ManagedChannelBuilder
                .forTarget("dynamic")
                .defaultLoadBalancingPolicy("round_robin")
                .usePlaintext()
                .build();

        CookServiceClientMultiple client = new CookServiceClientMultiple(channel);

        while (true) {
            String res = client.ping();
            System.out.println(res);
            Thread.sleep(1000);
        }
    }
}
