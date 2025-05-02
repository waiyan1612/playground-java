FROM amazoncorretto:24

ARG JAR_PATH
RUN test -n "$JAR_PATH" || (echo "JAR_PATH not provided" && false)

COPY $JAR_PATH/app-*-SNAPSHOT.jar /playground/app.jar
EXPOSE 8080

ENTRYPOINT ["java", "-jar", "/playground/app.jar"]
