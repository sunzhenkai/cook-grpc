[ -e java-proto-lib-out ] && rm -r java-proto-lib-out
mkdir java-proto-lib-out
protoc --java_out=java-proto-lib-out greeter.proto
