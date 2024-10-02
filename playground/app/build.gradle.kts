plugins {
    // From buildSrc
    id("playground.java-conventions")
    id("playground.spring-conventions")
}

group = "com.example"
version = "0.0.1-SNAPSHOT"

dependencies {
    implementation("org.springframework.boot:spring-boot-starter-actuator")
    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation(project(":lib"))

    testImplementation("org.springframework.boot:spring-boot-starter-test")
}
