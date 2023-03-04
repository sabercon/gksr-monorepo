dependencies {
    api(project(":lib:util"))
    api("org.springframework.boot:spring-boot-starter-data-mongodb-reactive")

    testImplementation(project(":lib:test"))
}
