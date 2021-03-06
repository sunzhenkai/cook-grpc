package pub.wii.cook.client;

import io.grpc.*;
import pub.wii.cook.v1.CookServiceClient;
import pub.wii.cook.v1.CookServiceGrpc;
import pub.wii.cook.v1.PingRequest;
import pub.wii.cook.v1.stub.CookServiceStubSettings;

import java.util.concurrent.TimeUnit;

public class CookServiceStubClient {

    private final CookServiceGrpc.CookServiceBlockingStub bs;

    public CookServiceStubClient(Channel channel) {
        this.bs = CookServiceGrpc.newBlockingStub(channel);
    }

    public String ping() {
        return this.bs.ping(PingRequest.newBuilder().setMsg("ping").build()).getRes();
    }

    public static void main(String[] args) {
        String target = "localhost:9000";
        ManagedChannel channel = ManagedChannelBuilder.forTarget(target)
                .usePlaintext()
                .build();
        CookServiceStubClient client = new CookServiceStubClient(channel);
        String res = client.ping();
        System.out.println(res);
    }
}
