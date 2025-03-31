# 🔥 Stage 1: Build the Spring Boot JAR
FROM eclipse-temurin:17-jdk AS builder
WORKDIR /app

# Copy the Gradle or Maven project files and download dependencies
COPY ../../build.gradle.kts ./
# download and cache dependencies
RUN ./gradlew dependencies

# Copy source code and build the application
COPY ../.. .
RUN ./gradlew clean build -x test

# 🔥 Stage 2: Use Distroless to Run the App Securely
FROM gcr.io/distroless/java17-debian12:nonroot-amd64
WORKDIR /app

# Copy the built JAR from the builder stage
COPY --from=builder /app/build/libs/app.jar app.jar

# Set the entrypoint to run the Spring Boot app
CMD ["app.jar"]
