# Stage 1
FROM eclipse-temurin:21-jdk AS builder
WORKDIR /app
COPY . .
RUN chmod +x ./mvnw && ./mvnw clean package -DskipTests

# Stage 2
FROM eclipse-temurin:21-jre-alpine
WORKDIR /app
COPY --from=builder /app/target/lab_5-*.jar app.jar
EXPOSE 5000
ENTRYPOINT ["java", "-jar", "app.jar"]