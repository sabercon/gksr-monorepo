dependencies {
    api(project(":lib:util"))
    api("org.springframework.boot:spring-boot-starter-test")
    api("io.projectreactor:reactor-test")

    api("io.kotest:kotest-runner-junit5")
    api("io.kotest:kotest-assertions-core")
    api("io.kotest:kotest-assertions-json")
    api("io.kotest:kotest-framework-datatest")
    api("io.kotest:kotest-property")
    api("io.kotest.extensions:kotest-extensions-spring")
    api("io.mockk:mockk")

    api("org.testcontainers:junit-jupiter")
    api("org.testcontainers:postgresql")
    api("org.testcontainers:mongodb")
    api("org.testcontainers:mockserver")
    api("org.mock-server:mockserver-client-java")

    // This should be replaced with smaller dependencies in the future
    compileOnly("org.springframework.boot:spring-boot-starter-data-r2dbc")
    compileOnly("org.springframework.boot:spring-boot-starter-data-mongodb-reactive")
}
