FROM eclipse-temurin:21-jdk-alpine
WORKDIR /app
ADD https://github.com/open-telemetry/opentelemetry-java-instrumentation/releases/download/v2.6.0/opentelemetry-javaagent.jar /otel-javaagent.jar
COPY target/filmservice-0.0.1-SNAPSHOT.jar app.jar
EXPOSE 8086
ENTRYPOINT ["java", "-javaagent:/otel-javaagent.jar", "-jar", "app.jar"]
