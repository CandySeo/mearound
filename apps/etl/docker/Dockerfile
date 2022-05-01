FROM openjdk:8-jdk-alpine
CMD ["./gradlew", "clean", "build"]
ARG JAR_FILE_PATH=build/libs/*.jar
COPY ${JAR_FILE_PATH} app.jar
COPY build/resources/main/data data
ENTRYPOINT ["java", "-jar", "app.jar"]