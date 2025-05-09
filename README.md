# playground-java

[![CI](https://github.com/waiyan1612/playground-java/actions/workflows/ci.yaml/badge.svg?branch=main)](https://github.com/waiyan1612/playground-java/actions/workflows/ci.yaml)

A playground to quickly run java POCs. The stack includes
- SpringBoot
- Valkey as an in-memory cache
- Flyway for schema migration
- OpenAPI and SwaggerUI
- OpenTelemetry (requires [jaeger](https://github.com/jaegertracing/jaeger) containers)
- Sonar for code quality (requires [sonarqube](https://github.com/SonarSource/docker-sonarqube) containers)

## Quick Start

1. Launch the accompanying containers. 
    ```bash
    ## Launch only postgres and valkey
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

### Running the java app via Docker 
Check out [build-and-run-container.sh](build-and-run-container.sh).

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

Tracing can be toggled via `management.tracing.enabled` in [application.yaml](playground/app/src/main/resources/application.yaml).

---

## Authentication and Authorization

- A SecurityFilterChain is defined in the [SecurityConfig](playground/app/src/main/java/com/example/playground/base/config/SecurityConfig.java) class with the following settings.
  - CSRF disabled.
  - CORS enabled for all ports on localhost.
- Two authentication methods are configured.
  - OAuth2 authentication using JWT - A dummy implementation that will return `sub:admin@jwt` for _any_ bearer token.
  - Basic authentication defined in [application.yaml](playground/app/src/main/resources/application.yaml).
    - `spring.security.user.name` - `admin@basic`
    - `spring.security.user.password` - `admin`
- The example use of `@PreAuthorize` can be found in the [HiController](playground/app/src/main/java/com/example/playground/hello/controller/HiController.java) class.

---

## Aspect

- AOP has been implemented around the controllers to log API requests and responses.
- Implementation can be found in the [AuditAspect](playground/app/src/main/java/com/example/playground/base/aspect/AuditAspect.java) class. 
  - It will hook into any beam ending with `Controller` under the package `com.example.playground`.
  - `@Before` / `@Around` annotations are used to control when we want the aspect to trigger.
 
---

## Hibernate

See [Hibernate notes](hibernate.md).

---

## API Documentation

- `app` exposes [Swagger UI](http://localhost:8080/swagger-ui/index.html#/) following the [OpenAPI specifications](http://localhost:8080/v3/api-docs).
- OpenAPI related configs are defined in the [OpenApiConfig](playground/app/src/main/java/com/example/config/OpenApiConfig.java) bean.

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
It's also recommended to install the [SonarQube / SonarLint](https://github.com/SonarSource/sonarlint-intellij) plugin if it's available for your IDE.

---

## Refs

- https://github.com/android/nowinandroid
- https://github.com/mrclrchtr/gradle-kotlin-spring
