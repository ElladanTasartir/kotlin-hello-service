FROM eclipse-temurin:17-jdk-jammy AS builder

WORKDIR /app

COPY gradle gradle
COPY gradlew .
COPY build.gradle.kts settings.gradle.kts ./

RUN ./gradlew --no-daemon dependencies || true

COPY . .

RUN ./gradlew build --no-daemon

FROM eclipse-temurin:17-jre-jammy

WORKDIR /app

COPY --from=builder /app/build/libs/hello.jar app.jar

EXPOSE 8080

CMD ["java", "-jar", "app.jar"]