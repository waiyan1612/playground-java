plugins {
    id("org.sonarqube")
}

sonar {
    properties {
        property("sonar.projectKey", "playground")
        property("sonar.host.url", "http://localhost:9000")
        property("sonar.login", "admin")
        property("sonar.password", "admin")
    }
}
