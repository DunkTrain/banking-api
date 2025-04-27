FROM eclipse-temurin:21-jdk-alpine

WORKDIR /app

COPY target/banking-api-1.12.0.jar app.jar

EXPOSE 8080

ENV JAVA_OPTS="-XX:MaxRAMPercentage=75"

ENTRYPOINT ["sh", "-c", "java $JAVA_OPTS -jar /app/app.jar"]
