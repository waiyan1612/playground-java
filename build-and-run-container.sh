#!/bin/bash

# Build Jar
cd playground
./gradlew build

# Build Docker Image
cd ..
docker build -t playground-java:latest --build-arg JAR_PATH=playground/app/build/libs  .

# Run Docker Image
docker run \
--rm \
-e SPRING_DATASOURCE_URL=jdbc:postgresql://host.docker.internal:5433/playground \
-e SPRING_PROFILES_ACTIVE=local \
-p 8080:8080 \
-m 512m \
--cpus "0.5" \
--name playground-java-app \
playground-java
