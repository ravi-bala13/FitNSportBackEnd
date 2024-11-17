# Build stage
FROM maven:3.9.6-openjdk-17 AS build

# Set the working directory
WORKDIR /app

# Copy the pom.xml and install dependencies
COPY pom.xml ./
RUN mvn dependency:go-offline -B

# Copy the rest of the project and build the application
COPY . ./
RUN mvn clean package -Pprod -DskipTests

# Package stage
FROM openjdk:17-jdk

# Set the working directory
WORKDIR /app

# Copy the built jar from the build stage
COPY --from=build /app/target/server-0.0.1-SNAPSHOT.jar server.jar

# Expose the port the app runs on
EXPOSE 8080

# Run the application
ENTRYPOINT ["java", "-jar", "server.jar"]
