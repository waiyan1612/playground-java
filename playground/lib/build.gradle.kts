plugins {
    // From buildSrc
    id("playground.java-conventions")
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

}
