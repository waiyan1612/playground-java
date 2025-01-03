# playground-java

[![CI](https://github.com/waiyan1612/playground-java/actions/workflows/ci.yaml/badge.svg?branch=main)](https://github.com/waiyan1612/playground-java/actions/workflows/ci.yaml)

A playground to quickly run java POCs. The stack includes
- SpringBoot
- Flyway for schema migration
- OpenAPI and SwaggerUI
- OpenTelemetry (requires [jaeger](https://github.com/jaegertracing/jaeger) containers)
- Sonar for code quality (requires [sonarqube](https://github.com/SonarSource/docker-sonarqube) containers)

## Quick Start

1. Launch the accompanying containers. 
    ```bash
    ## Launch only postgres
    docker compose -f docker/docker-compose.yaml up
    
    ## Launch all containers. 
    # --build is required for the sonar container.
    docker compose \
      -f docker/docker-compose.yaml \
      -f docker/docker-compose-telemetry.yaml \
      -f docker/docker-compose-sonar.yaml \
      up --build  
    ```

2. Build the jars.
    ```bash
    cd playground
    ./gradlew app:bootJar
    ./gradlew lib:jar
    ```

3. Seed the database.
    ```bash
    ./gradlew app:flywayMigrate
    ```

Other commonly used commands.
```shell
# Update gradlelock file
./gradlew app:dependencies --write-locks

# Wipe flyway
FLYWAY_CLEAN_DISABLED=false ./gradlew app:flywayClean
```    

---

## Contents
```
|__ docker          -> docker-compose files
|__ playground      -> gradle multi-project
  |__ app           -> spring boot app
  |__ db            -> flyway migration scripts
  |__ buildSrc      -> defines conventions which are reused in the `plugins` of subprojects
  |__ lib           -> standalone module built as a lib release 
```

### Version Catalog
[Version Catalog](playground/gradle/libs.versions.toml) is used to centralize the dependencies. [buildSrc](playground/buildSrc) is used to generate the conventions that can be reused in subprojects.

  | Convention            | Description                               | Enabled      |
  |-----------------------|-------------------------------------------|--------------|
  | `flyway-conventions`  | Configure Flyway (No Spring integration). | `app`        |
  | `java-conventions`    | Configure JDK and dependency locking.     | `app`, `lib` |
  | `sonar-conventions`   | Configure SonarQube.                      | `lib`        |
  | `spring-conventions`  | Configure SpringBoot.                     | `app`        |
  | `testing-conventions` | Configure JUnit.                          | `app`, `lib` |

### Dependency Locking

STRICT Dependency locking is enabled in [java-conventions](playground/buildSrc/src/main/kotlin/playground.java-conventions.gradle.kts). This can be overridden or even deactivated in the subproject ([example](playground/lib/build.gradle.kts)). `gradle.lockfile` can be generated via

```bash
./gradlew app:dependencies --write-locks
```
---

## Observability

### Logging

We are replacing the `logback` in `app` with `log4j2`. The logging properties are defined in `logging.config` in [application.yaml](playground/app/src/main/resources/application.yaml).
- `log4j2.xml` - uses `JsonTemplateLayout`. Suitable for integrating with external log ingestors.
- `log4j2-local.xml` - uses `PatternLayout`.

### Telemetry

- [Jaeger Traces](http://localhost:16686/search) - Telemetry spans, traces and logs are collected by Jaeger and persisted in a trace storage.  
- [Jaeger Monitor](http://localhost:16686/monitor) - Jaeger is also [configured](docker/.jaeger/config.yaml) to generate the metrics from the spans and to be scrapable from [Prometheus](http://localhost:9090). As such, There is no need for the springboot app to expose a metrics endpoint.

---

## API Documentation

- `app` exposes the [OpenAPI specification](http://localhost:8080/v3/api-docs) and [Swagger UI](http://localhost:8080/swagger-ui/index.html#/).

---

## Testing

- `app` uses Spring Boot test.`@SpringBootTest` will launch the entire app while `@ExtendWith(MockitoExtension.class)`
  will only inject the mocks and is more lightweight.
- `lib` uses traditional JUnit and Mockito. It has also been configured with the sonar plugin.

### Sonar Scanner

1. Start the sonarqube docker containers via compose (See [Quick Start](#Quick-Start) for details). The compose file has a helper service to reset the `admin`
   password and create the `playground` project at http://localhost:9000/dashboard?id=playground. The credentials are `admin:admin`.
    
2. For simplicity, we will use `sonar.login` and `sonar.password` to authenticate.
    ```bash 
    ./gradlew lib:test lib:jacocoTestReport lib:sonar
    ```
It's also recommended to install the `sonar-lint` plugin if it's available for your IDE.

---

## Refs

- https://github.com/android/nowinandroid
- https://github.com/mrclrchtr/gradle-kotlin-spring
