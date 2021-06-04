package pub.wii.cook.gapic.service;

import io.grpc.Server;
import io.grpc.ServerBuilder;
import wii.pub.gapic.cook.v1.CookServiceGrpc;
import wii.pub.gapic.cook.v1.PingResponse;

import java.io.IOException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class CookServiceImplMultiple extends CookServiceGrpc.CookServiceImplBase {
    private static final int ns = 3;
    private static final ExecutorService executorService = Executors.newFixedThreadPool(ns);
    private final String name;

    public CookServiceImplMultiple(String name) {
        this.name = name;
        System.out.println("name: " + name);
    }

    @Override
    public void ping(wii.pub.gapic.cook.v1.PingRequest request, io.grpc.stub.StreamObserver<PingResponse> responseObserver) {
        System.out.println("received message on " + name + ": " + request.getMsg());
        PingResponse response = PingResponse.newBuilder().setRes(request.getMsg() + ": pong from " + name).build();
        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }

    public static void start() {
        for (int i = 0; i < ns; ++i) {
            final int no = i;
            executorService.submit(() -> {
                try {
                    start("server_" + no, 9900 + no);
                } catch (Exception ignored) {
                }
            });
        }
    }

    public static void start(String name, int port) throws IOException, InterruptedException {
        Server server = ServerBuilder.forPort(port)
                .addService(new CookServiceImplMultiple(name))
                .build()
                .start();
        System.out.println("start " + name + " with port " + port);
        server.awaitTermination();
    }

    public static void main(String[] args) throws IOException, InterruptedException {
        start();
    }
}
