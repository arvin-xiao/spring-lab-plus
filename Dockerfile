# Simple Dockerfile adding Maven and GraalVM Native Image compiler to the standard
# 镜像版本号来自 graalvm 官网 https://github.com/graalvm/container/pkgs/container/graalvm-ce
FROM ghcr.io/graalvm/graalvm-ce:ol8-java17-22.2.0

ADD https://jenkins.goodcol.com/files/apache-maven-3.8.6.tar /tools/
ADD https://jenkins.goodcol.com/files/native-image-installable-svm-java17-linux-amd64-22.2.0.jar /tools/
COPY . /build

WORKDIR /tools
RUN set -x && \
    tar -xvf apache-maven-*.tar && \
    rm -rf apache-maven-*.tar && \
    mv apache-maven-* maven && \
    mv native-image-installable-*.jar native-image-installable.jar && \
    export MAVEN_HOME=/tools/maven && \
    export PATH=$PATH:$MAVEN_HOME/bin && \
    gu -L install native-image-installable.jar && \
    mvn --version && \
    native-image --version && \
    cd /build && \
    mvn -Pnative clean package -DskipTests

# We use a Docker multi-stage build here in order to only take the compiled native Spring Boot App from the first build container
FROM oraclelinux:7-slim

MAINTAINER ARVIN.XIAO

# Add Spring Boot Native app spring-boot-graal to Container
COPY --from=0 "/build/target/spring-lab-plus" spring-lab-plus

ENV PORT=8080

# Fire up our Spring Boot Native app by default
CMD [ "sh", "-c", "./spring-lab-plus -Dserver.port=$PORT" ]
