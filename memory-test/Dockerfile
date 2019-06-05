FROM openjdk:10-slim
ADD target/memory-test-0.0.1-SNAPSHOT.jar app.jar
EXPOSE 8080
ENTRYPOINT java $JVM_OPTS -Xmx${XMX} -jar /app.jar