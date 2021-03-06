package pub.wii.cook.gapic.client;

import io.grpc.Channel;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import pub.wii.cook.v1.CookServiceGrpc;
import pub.wii.cook.v1.PingRequest;

import java.util.concurrent.TimeUnit;

public class CookServiceClient {

    private final CookServiceGrpc.CookServiceBlockingStub bs;

    public CookServiceClient(Channel channel) {
        this.bs = CookServiceGrpc.newBlockingStub(channel).withDeadlineAfter(1, TimeUnit.SECONDS);
    }

    public String ping() {
        return this.bs.ping(PingRequest.newBuilder().setMsg("ping").build()).getRes();
    }

    public static void main(String[] args) {
        String target = "localhost:9000";
        ManagedChannel channel = ManagedChannelBuilder.forTarget(target)
                .usePlaintext()
                .build();
        CookServiceClient client = new CookServiceClient(channel);
        String res = client.ping();
        System.out.println(res);
    }
}
