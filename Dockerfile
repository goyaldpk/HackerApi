FROM openjdk:11-jdk-slim
ARG JAR_FILE=target/*.jar
COPY ${JAR_FILE} app.jar
ENTRYPOINT ["java","-Dkafka.bootstrap-servers=host.docker.internal:9092","-Dspring.data.mongodb.host=host.docker.internal", "-jar","/app.jar"]
EXPOSE 9000/tcp
