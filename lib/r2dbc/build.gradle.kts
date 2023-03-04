dependencies {
    api(project(":lib:util"))
    api("org.springframework.boot:spring-boot-starter-data-r2dbc")
    api("org.flywaydb:flyway-core")
    api("org.postgresql:r2dbc-postgresql")

    implementation("org.springframework:spring-jdbc")
    implementation("org.postgresql:postgresql")

    testImplementation(project(":lib:test"))
}
