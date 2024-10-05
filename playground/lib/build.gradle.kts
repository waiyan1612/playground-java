plugins {
    // From buildSrc
    id("playground.java-conventions")
    id("playground.testing-conventions")
    id("playground.sonar-conventions")
}

group = "com.example"
version = "0.0.1-SNAPSHOT"

configurations {
    all {
        resolutionStrategy.deactivateDependencyLocking()
    }
}

dependencies {

    testImplementation(platform(libs.junit.bom))
    testImplementation(libs.junit.api)
    testRuntimeOnly(libs.junit.engine)

    testImplementation(platform(libs.mockito.bom))
    testImplementation(libs.mockito.core)
    testImplementation(libs.mockito.junit)

}
