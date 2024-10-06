plugins {
    id("org.flywaydb.flyway")
}

flyway {
    url = "jdbc:postgresql://localhost:5433/playground"
    user = "flyway"
    password = "flyway"
    schemas = arrayOf("sandbox")
    locations = arrayOf("filesystem:../db")
}
