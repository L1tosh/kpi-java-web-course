# Stage 1: Build
FROM eclipse-temurin:21-jdk-alpine AS build
WORKDIR /app

COPY gradlew .
COPY gradle gradle
COPY build.gradle .
COPY settings.gradle ./

RUN chmod +x gradlew && ./gradlew dependencies --no-daemon

COPY src src
RUN ./gradlew bootJar --no-daemon -x test

# Stage 2: Run (Легкий фінальний образ Alpine)
FROM eclipse-temurin:21-jre-alpine
WORKDIR /app

RUN adduser -D -g '' appuser
COPY --from=build --chown=appuser:appuser /app/build/libs/*.jar app.jar
USER appuser

# Змінні за замовчуванням (можуть бути перевизначені в docker-compose)
ENV DB_HOST=db \
    DB_PORT=5432 \
    DB_NAME=galactic_cats \
    DB_USER=postgres \
    DB_PASSWORD=root

EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]