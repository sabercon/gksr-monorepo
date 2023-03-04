dependencies {
    api(project(":lib:util"))
    api("org.springframework.boot:spring-boot-starter-webflux")
    api("org.springframework.boot:spring-boot-starter-actuator")

    testImplementation(project(":lib:test"))
}
