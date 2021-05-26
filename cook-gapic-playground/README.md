# Introduction
bin 部分文件仅适用于 macos，运行如下命令，可以生成文件。
```shell
$ bash gen-pipeline.sh
```

以下文件来自于程序生成。

| 文件 | 对应脚本 | 备注 | 
| ---- | ---- | ---- |
| greeter.proto.pb | gen-description.sh | - |
| greeter-gapic-config.yaml | gen-gapic-config.sh | 虽然由脚本生成，但是可以修改，修改后不要再重复生成 |