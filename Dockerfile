FROM openjdk:21
ADD . /app
WORKDIR /app
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "./target/StairWeb-0.0.1-SNAPSHOT.jar"]