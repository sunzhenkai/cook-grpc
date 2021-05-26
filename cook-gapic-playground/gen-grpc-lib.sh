[ -e java-grpc-lib-out ] && rm -r java-grpc-lib-out
mkdir java-grpc-lib-out
protoc --plugin=protoc-gen-grpc=/Users/wii/Downloads/protoc-gen-grpc-java-1.38.0-osx-x86_64.exe --grpc_out=java-grpc-lib-out greeter.proto
