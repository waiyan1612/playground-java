# playground-java

A playground to quickly run java POCs.

## Contents

```
|__ docker          -> docker-compose file to easily bring up the postgres db and sonarqube
|__ playground      -> gradle multi-project
  |__ app           -> spring boot app
  |__ db            -> flyway migration scripts
  |__ buildSrc      -> defines conventions which are reused in the `plugins` of subprojects
  |__ lib           -> standalone module built as a lib release 
```

## Quick Start

Launch the postgres and sonarqube containers.

```bash
 docker compose -f docker/docker-compose.yaml -f docker/docker-compose-sonar.yaml up --build
```

Build the jars.

```bash
cd playground
./gradlew app:bootJar
./gradlew lib:jar
```

Seed DB.

```bash
./gradlew app:flywayMigrate
```

## Project Details

- [Version Catalog](playground/gradle/libs.versions.toml) is used to centralize the dependencies.
  [buildSrc](playground/buildSrc) is used to generate the conventions that can be reused in subprojects.

  | Convention            | Description                               | Enabled      |
  |-----------------------|-------------------------------------------|--------------|
  | `flyway-conventions`  | Configure Flyway (No Spring integration). | `app`        |
  | `java-conventions`    | Configure JDK and dependency locking.     | `app`, `lib` |
  | `sonar-conventions`   | Configure SonarQube.                      | `lib`        |
  | `spring-conventions`  | Configure SpringBoot.                     | `app`        |
  | `testing-conventions` | Configure JUnit.                          | `app`, `lib` |

### Flyway

Run Migrations.

```bash
./gradlew app:flywayMigrate
```

Clean up.

```bash
FLYWAY_CLEAN_DISABLED=false ./gradlew app:flywayClean
```

### Logging
We are replacing the `logback` in `app` with `log4j2`. The logging properties are defined in `logging.config` in [application.yaml](playground/app/src/main/resources/application.yaml).
- `log4j2.xml` - uses `JsonTemplateLayout`.
- `log4j2-local.xml` - uses `PatternLayout`.
This is configured in [application.yaml](playground/app/src/main/resources/application.yaml).

### Dependency Locking

STRICT Dependency locking is enabled
in [java-conventions](playground/buildSrc/src/main/kotlin/playground.java-conventions.gradle.kts). This can be
overridden in the subproject ([example](playground/lib/build.gradle.kts)).

`gradle.lockfile` can be generated via

```bash
./gradlew app:dependencies --write-locks
```

### Testing

- `app` uses Spring Boot test.`@SpringBootTest` will launch the entire app while `@ExtendWith(MockitoExtension.class)`
  will only inject the mocks and is more lightweight.
- `lib` uses traditional JUnit and Mockito. It has also been configured with the sonar plugin.

### API Documentation

- `app` exposes the [OpenAPI specification](http://localhost:8080/v3/api-docs) and [Swagger UI](http://localhost:8080/swagger-ui/index.html#/) 

#### Sonar Scanner

1. Start the sonarqube docker containers via compose. The compose file has a helper service to reset the `admin`
   password and create the `playground` project at http://localhost:9000/dashboard?id=playground.
    ```bash
    docker compose -f docker/docker-compose.yaml up --build
    ```
2. For simplicity, we will use `sonar.login` and `sonar.password` to authenticate.
    ```bash 
    ./gradlew lib:test lib:jacocoTestReport lib:sonar
    ```

It's also recommended to install the `sonar-lint` plugin if it's available for your IDE.

## Refs

- https://github.com/android/nowinandroid
- https://github.com/mrclrchtr/gradle-kotlin-spring
