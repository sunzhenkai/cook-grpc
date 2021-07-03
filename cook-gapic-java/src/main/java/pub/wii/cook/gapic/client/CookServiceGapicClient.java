package pub.wii.cook.gapic.client;

import com.google.api.gax.grpc.GrpcCallContext;
import com.google.api.gax.grpc.GrpcTransportChannel;
import com.google.api.gax.rpc.ApiCallContext;
import com.google.api.gax.rpc.ClientContext;
import com.google.api.gax.rpc.TransportChannel;
import io.grpc.CallOptions;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import pub.wii.gapic.cook.v1.CookServiceClient;
import pub.wii.gapic.cook.v1.CookServiceSettings;
import pub.wii.gapic.cook.v1.PingRequest;
import pub.wii.gapic.cook.v1.PingResponse;

import java.sql.Time;
import java.util.concurrent.TimeUnit;

public class CookServiceGapicClient {
    public static void main(String[] args) throws Exception {
        String target = "127.0.0.1:9000";

        ManagedChannel channel = ManagedChannelBuilder
//                .forTarget(target)
                .forAddress("127.0.0.1", 9000)
                .usePlaintext()
                .build();

        TransportChannel tc = GrpcTransportChannel.create(channel);
        CallOptions co = CallOptions.DEFAULT
                .withDeadlineAfter(1, TimeUnit.SECONDS);

        ApiCallContext acc = GrpcCallContext.createDefault()
                .withTransportChannel(tc)
                .withChannel(channel)
                .withCallOptions(co);
        ClientContext cc = ClientContext.newBuilder()
                .setDefaultCallContext(acc)
                .setTransportChannel(tc)
                .build();


//        TransportChannelProvider tcp = FixedTransportChannelProvider.create(tc);
//        CookServiceGrpc.CookServiceBlockingStub stub = CookServiceGrpc.newBlockingStub(channel);
        CookServiceSettings settings = CookServiceSettings.newBuilder(cc).build();
//        CookServiceStubSettings css = CookServiceStubSettings.newBuilder()
//                .setTransportChannelProvider(tcp)
//                .setCredentialsProvider(new NoCredentialsProvider())
//                .build();
//        CookServiceClient client = CookServiceClient.create(stub);
        CookServiceClient client = CookServiceClient.create(settings);
//        CookServiceClient client = CookServiceClient.create(css.createStub());
        PingResponse response = client.ping(PingRequest.newBuilder().setMsg("ping").build());
        String res = response.getRes();
        System.out.println(res);
    }
}
