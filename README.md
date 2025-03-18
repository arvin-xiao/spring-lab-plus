### 1、官网

```
Spring6.0新特性：https://github.com/spring-projects/spring-framework/wiki/What%27s-New-in-Spring-Framework-6.x

SpringBoot3.0：https://docs.spring.io/spring-boot/docs/current/reference/html/
```
### 2、安装GraalVM

graalvm-jdk 官方下载地址：https://github.com/graalvm/graalvm-ce-builds/releases/tag/vm-22.1.0

native-iamge 官方下载地址：https://github.com/graalvm/graalvm-ce-builds/releases/tag/vm-22.1.0


下载地址：https://github.com/graalvm/graalvm-ce-builds/releases 按照jdk版本下载GraalVM。SpringBoot3.0必须要使用jdk17以上

安装成功之后，使用java -version，可以看到VM是GraalVM

### 3、安装native-image

```shell
# 命令操作【离线方式】

gu install --file native-image-installable-svm-java17-windows-amd64-22.1.0.jar

# 或者在命令中直接【在线】联网按照（推荐）

gu install native-image

# 查看已经安装的 native-image 版本

native-image --version

```

### 4、GraalVM的限制

GraalVM在编译成二进制可执行文件时，需要确定该应用到底用到了哪些类、哪些方法、哪些属性，从而把这些代码编译为机器指令（也就是exe文件）。但是我们一个应用中某些类可能是动态生成的，也就是应用运行后才生成的，为了解决这个问题，GraalVM提供了配置的方式，比如我们可以在编译时告诉GraalVM哪些方法会被反射调用，比如我们可以通过reflect-config.json来进行配置

```
## 注意需要使用GraalVM环境，需要有C语言环境，最好使用linux系统

mvn native:compile -Pnative

mvn clean package -DskipTests -Pnative
```
