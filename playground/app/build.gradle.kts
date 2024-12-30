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
    implementation(libs.spring.boot.actuator)
    implementation(libs.spring.boot.web)
    implementation(libs.spring.boot.data.jpa)

    implementation("org.springframework.boot:spring-boot-starter-log4j2")
    modules {
        module("org.springframework.boot:spring-boot-starter-logging") {
            replacedBy("org.springframework.boot:spring-boot-starter-log4j2", "Use Log4j2 instead of Logback")
        }
    }

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

    testImplementation(libs.spring.boot.test)
}
