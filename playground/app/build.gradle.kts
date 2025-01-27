plugins {
    // From buildSrc
    id("playground.java-conventions")
    id("playground.spring-conventions")
    id("playground.flyway-conventions")
    id("playground.testing-conventions")
}

group = "com.example"
version = "0.0.1-SNAPSHOT"

dependencies {
    implementation(project(":lib"))

    implementation("org.springframework.boot:spring-boot-starter-actuator")
    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("org.springframework.boot:spring-boot-starter-data-jpa")
    implementation("org.springframework.boot:spring-boot-starter-security")
    implementation("org.springframework.boot:spring-boot-starter-oauth2-resource-server")

    implementation("org.springframework.boot:spring-boot-starter-log4j2")
    modules {
        module("org.springframework.boot:spring-boot-starter-logging") {
            replacedBy("org.springframework.boot:spring-boot-starter-log4j2", "Use Log4j2 instead of Logback")
        }
    }
    implementation(libs.valkey)

    // For JSON Log layout
    runtimeOnly("org.apache.logging.log4j:log4j-layout-template-json")

    // For Docs
    implementation(libs.spring.doc)

    // For Telemetry
    implementation(libs.micrometer.bridge)
    implementation(libs.otel.exporter)

    implementation(libs.mapstruct)
    annotationProcessor(libs.mapstruct.processor)

    runtimeOnly(libs.postgres)

    testImplementation("org.springframework.boot:spring-boot-starter-test")
    testImplementation(libs.h2)

    developmentOnly("org.springframework.boot:spring-boot-devtools")
}
