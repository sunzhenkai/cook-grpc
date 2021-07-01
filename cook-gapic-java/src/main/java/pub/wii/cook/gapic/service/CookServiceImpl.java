package pub.wii.cook.gapic.service;

import io.grpc.Server;
import io.grpc.ServerBuilder;
import lombok.SneakyThrows;
import wii.pub.gapic.cook.v1.CookServiceGrpc;
import wii.pub.gapic.cook.v1.PingResponse;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

public class CookServiceImpl extends CookServiceGrpc.CookServiceImplBase {
    private Server server;

    @SneakyThrows
    @Override
    public void ping(wii.pub.gapic.cook.v1.PingRequest request, io.grpc.stub.StreamObserver<wii.pub.gapic.cook.v1.PingResponse> responseObserver) {
        System.out.println("received message: " + request.getMsg());
        PingResponse response = PingResponse.newBuilder().setRes(request.getMsg() + ": pong").build();

        Thread.sleep(1000 * 100);

        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }

    public void start() throws IOException {
        int port = 9000;
        server = ServerBuilder.forPort(port)
                .addService(this)
                .build()
                .start();
    }

    public void stop() throws InterruptedException {
        server.shutdown().awaitTermination(30, TimeUnit.SECONDS);
    }

    public void block() throws InterruptedException {
        server.awaitTermination();
    }

    public static void main(String[] args) throws IOException, InterruptedException {
        CookServiceImpl impl = new CookServiceImpl();
        impl.start();
        impl.block();
    }
}
