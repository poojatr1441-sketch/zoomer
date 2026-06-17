#Give me Linux + Java 17 - borrowing an existing image.
#base image for Java applications
FROM eclipse-temurin:17

#cd app  - creates /app inside container -current working directory inside the container.
WORKDIR /app

#Take my JAR and Put inside container
COPY target/Zoomer-0.0.1-SNAPSHOT.jar app.jar

#metadata - Application listens on 8080 - Spring Boot runs on server.port=8080
EXPOSE 8080

#run a jar file  java -jar app.jar - main command that runs when the container starts.
ENTRYPOINT ["java", "-jar", "app.jar"]


#Source Code
 #    ↓
#mvn clean package
 #    ↓
#Zoomer.jar
 #    ↓
#Dockerfile
 #    ↓
#docker build
 #    ↓
#Docker Image
 #    ↓
#docker run
 #    ↓
#Container