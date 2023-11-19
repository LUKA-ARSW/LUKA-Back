# First stage: Maven dependency download
FROM maven:3.8.5-openjdk-17-slim AS dependency
COPY pom.xml .
RUN mvn dependency:go-offline --batch-mode

# Second stage: Maven build
FROM dependency AS build
COPY src ./src
RUN mvn package --batch-mode -DskipTests

# Third stage: Java runtime
FROM openjdk:17-jdk-alpine AS runtime
WORKDIR /app
COPY --from=build target/*.jar lukaBack-1.0.0.jar
EXPOSE $SERVER_PORT
ENTRYPOINT ["java", "-jar", "lukaBack-1.0.0.jar"]