OUTPUT=java-proto-lib-out
[ -e $OUTPUT ] && rm -r $OUTPUT
mkdir $OUTPUT
protoc --java_out=$OUTPUT greeter.proto
