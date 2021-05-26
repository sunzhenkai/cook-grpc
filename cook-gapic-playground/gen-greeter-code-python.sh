[ -e python-code-gen ] && rm -r python-code-gen
mkdir python-code-gen
java -cp /Users/wii/Downloads/gapic-generator-2.11.1/build/libs/gapic-generator-2.11.1-fatjar.jar \
    com.google.api.codegen.GeneratorMain LEGACY_GAPIC_AND_PACKAGE \
    --descriptor_set=greeter.proto.pb \
    --service_yaml=greeter-service.yaml \
    --gapic_yaml=greeter-gapic-config.yaml \
    --language=python \
    --package_yaml2=greeter-meta-config.yaml \
    --o=python-code-gen
