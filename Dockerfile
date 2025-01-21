# Stage 1: Build the application
FROM maven:3.9.6-eclipse-temurin-21 AS build
WORKDIR /app
COPY . .
RUN mvn package -DskipTests

# Stage 2: Create a smaller image with only the JAR file and Playwright binaries
FROM eclipse-temurin:21-jre
WORKDIR /app

# Install maven (required to install Playwright)
RUN apt-get update && apt-get install -y maven && apt-get clean

# Copy the application JAR and pom (required to install Playwright)
COPY --from=build /app/target/*.jar app.jar
COPY --from=build /app/pom.xml pom.xml

# Download Playwright dependencies (for chromium browser)
RUN mvn exec:java -e -D exec.mainClass=com.microsoft.playwright.CLI -D exec.args="install-deps chromium"

# Start the app
ENTRYPOINT ["java", "-jar", "app.jar"]
