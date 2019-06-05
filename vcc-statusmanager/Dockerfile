FROM openjdk:10-slim
ADD target/vcc-statusmanager-0.0.1-SNAPSHOT.jar app.jar
EXPOSE 8080
ARG XMX
ENV XMX ${XMX:-384m}
ENTRYPOINT java $JVM_OPTS -jar -Xmx${XMX} -Djava.security.egd=file:/dev/./urandom /app.jar