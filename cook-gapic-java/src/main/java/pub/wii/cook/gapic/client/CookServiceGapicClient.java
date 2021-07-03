package pub.wii.cook.gapic.client;

import com.google.api.gax.grpc.GrpcCallContext;
import com.google.api.gax.grpc.GrpcTransportChannel;
import com.google.api.gax.rpc.ApiCallContext;
import com.google.api.gax.rpc.ClientContext;
import com.google.api.gax.rpc.TransportChannel;
import io.grpc.CallOptions;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import org.threeten.bp.Duration;
import pub.wii.cook.v1.CookServiceClient;
import pub.wii.cook.v1.CookServiceSettings;
import pub.wii.cook.v1.PingRequest;
import pub.wii.cook.v1.PingResponse;

import java.util.concurrent.TimeUnit;

public class CookServiceGapicClient {
    public static void main(String[] args) throws Exception {
        String target = "127.0.0.1:9000";

        ManagedChannel channel = ManagedChannelBuilder
                .forTarget(target)
                .usePlaintext()
                .build();

        TransportChannel tc = GrpcTransportChannel.create(channel);
        CallOptions co = CallOptions.DEFAULT
                .withDeadlineAfter(1, TimeUnit.SECONDS);

        ApiCallContext acc = GrpcCallContext.createDefault()
                .withCallOptions(co);
        ClientContext cc = ClientContext.newBuilder()
                .setDefaultCallContext(acc)
                .setTransportChannel(tc)
                .build();

        CookServiceSettings.Builder sb = CookServiceSettings.newBuilder(cc);
        sb.pingSettings()
                .setRetrySettings(
                        sb.pingSettings().getRetrySettings().toBuilder()
                        .setTotalTimeout(Duration.ofSeconds(5))
                        .build()
                )
                .build();
        CookServiceClient client = CookServiceClient.create(sb.build());
        PingResponse response = client.ping(PingRequest.newBuilder().setMsg("ping").build());
        String res = response.getRes();
        System.out.println(res);
    }
}
