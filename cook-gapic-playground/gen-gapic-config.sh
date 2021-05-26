java -cp /Users/wii/Downloads/gapic-generator-2.11.1/build/libs/gapic-generator-2.11.1-fatjar.jar \
    com.google.api.codegen.GeneratorMain GAPIC_CONFIG \
    --descriptor_set=greeter.proto.pb \
    --service_yaml=greeter-service.yaml \
    -o=/Users/wii/Data/tmp/grpc/greeter-gapic-config.yaml
