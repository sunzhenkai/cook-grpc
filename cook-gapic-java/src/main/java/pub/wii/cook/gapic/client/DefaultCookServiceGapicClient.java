package pub.wii.cook.gapic.client;

import com.google.api.gax.core.NoCredentialsProvider;
import com.google.api.gax.grpc.GrpcTransportChannel;
import com.google.api.gax.rpc.FixedTransportChannelProvider;
import com.google.api.gax.rpc.TransportChannel;
import com.google.api.gax.rpc.TransportChannelProvider;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import pub.wii.cook.v1.CookServiceClient;
import pub.wii.cook.v1.CookServiceSettings;
import pub.wii.cook.v1.PingRequest;
import pub.wii.cook.v1.PingResponse;
import pub.wii.cook.v1.stub.CookServiceStubSettings;

public class DefaultCookServiceGapicClient {
    public static void main(String[] args) throws Exception {
        String target = "127.0.0.1:9000";

        ManagedChannel channel = ManagedChannelBuilder
                .forTarget(target)
                .usePlaintext()
                .build();

        CookServiceSettings cs = CookServiceSettings.create(CookServiceStubSettings.newBuilder()
                .setCredentialsProvider(new NoCredentialsProvider())
                .build());
        CookServiceClient client = CookServiceClient.create(cs);
        System.out.println(client.getSettings());
        System.out.println(client.getSettings().getStubSettings());
//        PingResponse response = client.ping(PingRequest.newBuilder().setMsg("ping").build());
//        String res = response.getRes();
//        System.out.println(res);
    }
}
