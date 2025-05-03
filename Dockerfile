FROM openjdk:17
WORKDIR /app
EXPOSE 9090
ADD target/TwoMicroservices-0.0.1-SNAPSHOT.jar two-microservices.jar
ENTRYPOINT ["java", "-jar", "two-microservices.jar"]