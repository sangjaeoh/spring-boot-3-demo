# 멀티모듈에서 루트 폴더를 빌드
# 빌드
FROM openjdk:17-jdk-slim AS builder
WORKDIR /app

# 소스 코드 복사
COPY . .

# 권한 부여
RUN chmod +x ./gradlew

# Gradle 빌드
RUN ./gradlew --no-daemon clean :api:demo-api:bootJar --refresh-dependencies

# 런타임 스테이지: 최종 이미지
FROM openjdk:17-jdk-slim

# 빌드 결과물 복사
COPY --from=builder /app/api/demo-api/build/libs/*.jar app.jar

# 컨테이너 시작 시 실행할 명령어 설정
ENTRYPOINT ["java", "-jar", "/app.jar"]

