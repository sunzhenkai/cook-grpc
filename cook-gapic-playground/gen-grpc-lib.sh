OUTPUT=java-grpc-lib-out
[ -e $OUTPUT ] && rm -r $OUTPUT
mkdir $OUTPUT
protoc --plugin=protoc-gen-grpc=bin/protoc-gen-grpc-java-1.38.0-osx-x86_64.exe \
    --grpc_out=$OUTPUT greeter.proto
