package pub.wii.cook.gapic.client;

import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import pub.wii.cook.v1.CookServiceClient;
import pub.wii.cook.v1.PingRequest;
import pub.wii.cook.v1.PingResponse;

public class SimpleCookServiceGapicClient {
    public static void main(String[] args) throws Exception {
        String target = "127.0.0.1:9000";

        ManagedChannel channel = ManagedChannelBuilder
                .forTarget(target)
                .usePlaintext()
                .build();

        CookServiceClient client = CookServiceClient.create();
        PingResponse response = client.ping(PingRequest.newBuilder().setMsg("ping").build());
        String res = response.getRes();
        System.out.println(res);
    }
}
