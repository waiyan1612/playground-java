# playground-java

A playground to quickly run java POCs.

## Contents

```
|__ docker          -> docker-compose file to easily bring up the postgres db and sonarqube
|__ playground      -> gradle multi-project
  |__ app           -> spring boot app
  |__ buildSrc      -> defines conventions which are reused in the `plugins` of subprojects
  |__ db            -> flyway migration scripts
  |__ lib           -> standalone module built as a lib release 
```

## Quick Start
Launch the postgres and sonarqube containers.
```bash
 docker compose -f docker/docker-compose.yaml up --build
```
Build the jars
```bash
cd playground
./gradlew app:bootJar
./gradlew lib:jar
```

## Project Details 
- [Version Catalog](playground/gradle/libs.versions.toml) is used to centralize the dependencies.
- [buildSrc](playground/buildSrc) is used to generate the conventions that can be reused in subprojects.

  | Convention            | Description                            | Enabled      |
  |-----------------------|----------------------------------------|--------------|
  | `java-conventions`    | Configure JDK and dependency locking   | `app`, `lib` |
  | `sonar-conventions`   | Configure SonarQube.                   | `lib`        |
  | `spring-conventions`  | Configure SpringBoot.                  | `app`        |
  | `testing-conventions` | Configure JUnit.                       |              |


### Dependency Locking
STRICT Dependency locking is enabled in [java-conventions](playground/buildSrc/src/main/kotlin/playground.java-conventions.gradle.kts). This can be overridden in the subproject ([example](playground/lib/build.gradle.kts)). 

`gradle.lockfile` can be generated via
```bash
./gradlew app:dependencies --write-locks
```

### Sonar Scanner

#### Prerequisite
Start the sonarqube docker containers via compose. The compose file has a helper service to reset the `admin` password and create the `playground` project at http://localhost:9000/dashboard?id=playground.
```bash
docker compose -f docker/docker-compose.yaml up --build
```
For simplicity, we will use `sonar.login` and `sonar.password` to authenticate.
```bash 
./gradlew lib:sonar
```

## Refs
- https://github.com/android/nowinandroid
- https://github.com/mrclrchtr/gradle-kotlin-spring
