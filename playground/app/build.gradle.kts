plugins {
    // From buildSrc
    id("playground.java-conventions")
    id("playground.spring-conventions")
    id("playground.testing-conventions")
}

group = "com.example"
version = "0.0.1-SNAPSHOT"

dependencies {
    implementation(project(":lib"))
    implementation(libs.spring.boot.actuator)
    implementation(libs.spring.boot.web)

    testImplementation(libs.spring.boot.test)
}
