#!/bin/bash
set -ex

bazel clean
bazel build //...
# bazel build //cook/v1:python_cook_gapic
# bazel build //cook/v1:java_cook_grpc
# bazel build //cook/v1:java_cook_gapic

# 拷贝打包产物
[ -e build ] && rm -r build
mkdir build

# java
cp bazel-out/darwin-fastbuild/bin/cook/v1/cook_proto-speed-src.jar build
cp bazel-out/darwin-fastbuild/bin/cook/v1/libcook_proto-speed.jar build
cp bazel-out/darwin-fastbuild/bin/cook/v1/java_cook_gapic_srcjar.srcjar build
cp bazel-out/darwin-fastbuild/bin/cook/v1/libjava_cook_gapic.jar build
cp bazel-out/darwin-fastbuild/bin/cook/v1/libjava_cook_grpc-src.jar build
cp bazel-out/darwin-fastbuild/bin/cook/v1/libjava_cook_grpc.jar build
# python
cp bazel-out/darwin-fastbuild/bin/cook/v1/python_cook_gapic.srcjar.zip build

# install python
# pip3 install build/python_cook_gapic.srcjar.zip
