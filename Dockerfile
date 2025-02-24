# ==========================
# 1. Build Stage (Gradle Build)
# ==========================
FROM gradle:latest AS build
WORKDIR /app

# 전체 프로젝트 복사 (파일 누락 방지)
COPY . .

# Gradle 캐시 활용을 위해 의존성 먼저 다운로드
RUN ./gradlew dependencies --no-daemon

# Spring Boot 애플리케이션 빌드
RUN ./gradlew clean bootJar --no-daemon

# ==========================
# 2. Runtime Stage (Slim JDK 21)
# ==========================
FROM openjdk:21-slim
WORKDIR /app

# 빌드된 JAR 복사
COPY --from=build /app/build/libs/*.jar app.jar

# 애플리케이션 실행
ENTRYPOINT ["java", "-jar", "app.jar"]
