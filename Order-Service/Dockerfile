# Stage 1: Build the application
FROM maven:3.8.4-openjdk-17 AS build
WORKDIR /app
COPY pom.xml .
COPY src ./src
RUN mvn clean package -DskipTests

# Stage 2: Create the final image
FROM openjdk:17-jdk-alpine
WORKDIR /app
# Use a wildcard to copy the jar file (it will pick the jar file dynamically)
COPY --from=build /app/target/*.jar app.jar
#EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]
