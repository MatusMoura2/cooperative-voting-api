# Build stage
FROM eclipse-temurin:17-jdk-alpine AS builder
WORKDIR /app
# Copy maven executable to the image
COPY mvnw .
COPY .mvn .mvn
# Copy the pom.xml file
COPY pom.xml .
# Build all the dependencies in preparation to go offline. 
# This is a separate step so the dependencies will be cached unless the pom.xml file has changed.
RUN ./mvnw dependency:go-offline -B
# Copy the project source
COPY src src
# Package the application
RUN ./mvnw package -DskipTests

# Run stage
FROM eclipse-temurin:17-jre-alpine
WORKDIR /app
COPY --from=builder /app/target/*.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]
