FROM gradle:8.4.0-jdk17 AS build
WORKDIR /gradle
COPY gradle ./gradle
COPY gradlew build.gradle settings.gradle ./
COPY src ./src
RUN chmod +x ./gradlew
RUN ./gradlew clean
RUN ./gradlew bootJar

FROM openjdk:17-jdk
WORKDIR /app
COPY --from=build /gradle/build/libs/*.jar /app.jar