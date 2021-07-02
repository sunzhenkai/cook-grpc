package pub.wii.cook.gapic.client;

import pub.wii.gapic.cook.v1.CookServiceClient;
import pub.wii.gapic.cook.v1.CookServiceSettings;
import pub.wii.gapic.cook.v1.PingRequest;
import pub.wii.gapic.cook.v1.PingResponse;

public class CookServiceGapicClient {
    public static void main(String[] args) throws Exception {
        String target = "127.0.0.1:9000";
        CookServiceSettings settings = CookServiceSettings.newBuilder()
                .setEndpoint(target)
                .build();
        CookServiceClient client = CookServiceClient.create(settings);
        PingResponse response = client.ping(PingRequest.newBuilder().setMsg("ping").build());
        String res = response.getRes();
        System.out.println(res);
    }
}
