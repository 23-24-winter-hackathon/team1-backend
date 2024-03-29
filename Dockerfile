FROM gradle:8.4.0-jdk17 AS build
WORKDIR /gradle
COPY . ./
#COPY gradle ./gradle
#COPY gradlew build.gradle settings.gradle ./
#COPY src ./src
RUN chmod +x gradlew
RUN ./gradlew clean
RUN ./gradlew build -x test

FROM openjdk:17-jdk
WORKDIR /app
COPY .env ./
COPY --from=build /gradle/build/libs/demo-0.0.1-SNAPSHOT.jar /app.jar
ADD https://raw.githubusercontent.com/vishnubob/wait-for-it/master/wait-for-it.sh /
RUN chmod +x /wait-for-it.sh