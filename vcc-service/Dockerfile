FROM openjdk:10-slim
ADD target/vcc-service-0.0.1-SNAPSHOT.jar app.jar
EXPOSE 8080
# ENTRYPOINT java $JVM_OPTS -jar -javaagent:/opt/appdynamics/javaagent.jar -Dappdynamics.agent.reuse.nodeName=true -Dappdynamics.agent.reuse.nodeName.prefix=${APPDYNAMICS_NODE_PREFIX} -Djava.security.egd=file:/dev/./urandom /app.jar
ENTRYPOINT java $JVM_OPTS -jar -Djava.security.egd=file:/dev/./urandom /app.jar