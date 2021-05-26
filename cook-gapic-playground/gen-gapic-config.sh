java -cp bin/gapic-generator-2.11.1-fatjar.jar \
    com.google.api.codegen.GeneratorMain GAPIC_CONFIG \
    --descriptor_set=greeter.proto.pb \
    --service_yaml=greeter-service.yaml \
    -o=$(pwd)/greeter-gapic-config.yaml
