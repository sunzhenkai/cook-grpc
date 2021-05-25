package(default_visibility = ["//visibility:public"])

load("@rules_proto//proto:defs.bzl", "proto_library")

# load(
    # "java_gapic_assembly_gradle_pkg",
    # "java_gapic_library",
    # "java_gapic_test",
    # "java_grpc_library",
    # "java_proto_library",
# )

java_proto_library(
    name = "library_java_proto",
    deps = [":library_proto"],
)


# java_grpc_library(
#     name = "library_java_grpc",
#     srcs = [":library_proto"],
#     deps = [":library_java_proto"],
# )

# java_gapic_library(
#     name = "library_java_gapic",
#     srcs = [":library_proto"],
#     gapic_yaml = "library_example_gapic.yaml",
#     test_deps = [
#         ":library_java_grpc",
#     ],
#     deps = [
#         ":library_java_proto",
#     ],
# )

# python_proto_library(
#     name = "person_python_proto",
#     deps = [":person_proto"]
# )

library_proto(
    name = "person_proto",
    srcs = ["person.proto"],
    deps = ["@com_google_protobuf//:any_proto"]
)