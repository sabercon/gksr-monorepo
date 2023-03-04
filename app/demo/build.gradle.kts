plugins {
    id("org.springframework.boot")
}

dependencies {
    implementation(project(":lib:web"))
    implementation(project(":lib:r2dbc"))
    implementation(project(":lib:mongo"))

    testImplementation(project(":lib:test"))
}
