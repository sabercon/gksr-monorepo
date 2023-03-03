import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    kotlin("jvm") version "1.7.22"
    kotlin("plugin.spring") version "1.7.22"
    id("org.springframework.boot") version "3.0.3" apply false
    id("io.spring.dependency-management") version "1.1.0" apply false

    id("io.gitlab.arturbosch.detekt") version "1.22.0"
    id("org.jetbrains.kotlinx.kover") version "0.6.1"
}

repositories {
    mavenCentral()
}

allprojects {
    apply(plugin = "org.jetbrains.kotlin.jvm")
    apply(plugin = "org.jetbrains.kotlin.plugin.spring")
    apply(plugin = "io.spring.dependency-management")
    apply(plugin = "io.gitlab.arturbosch.detekt")
    apply(plugin = "org.jetbrains.kotlinx.kover")

    group = "com.example"
    version = "0.0.1-SNAPSHOT"
    java.sourceCompatibility = JavaVersion.VERSION_17

    repositories {
        mavenCentral()
    }

    dependencies {
        constraints {
            implementation("com.google.guava:guava:31.1-jre")

            implementation("io.mockk:mockk:1.13.4")
            implementation("io.kotest.extensions:kotest-extensions-spring:1.1.2")
            implementation("org.mock-server:mockserver-client-java:5.11.2")
        }

        implementation(platform(org.springframework.boot.gradle.plugin.SpringBootPlugin.BOM_COORDINATES))
        implementation(platform("io.kotest:kotest-bom:5.5.5"))
        implementation(platform("org.testcontainers:testcontainers-bom:1.17.3"))

        detektPlugins("io.gitlab.arturbosch.detekt:detekt-formatting:1.22.0")
    }

    tasks.withType<KotlinCompile> {
        kotlinOptions {
            freeCompilerArgs = listOf("-Xjsr305=strict", "-Xjvm-default=all")
            jvmTarget = "17"
        }
    }

    tasks.withType<Test> {
        useJUnitPlatform()
    }

    detekt {
        autoCorrect = true
        buildUponDefaultConfig = true
        config = files("$rootDir/config/detekt/config.yml")
        baseline = file("$rootDir/config/detekt/baseline.xml")
    }
}

koverMerged {
    enable()

    filters {
        classes {
            includes.add("com.example.*")
        }
    }

    verify {
        rule {
            bound {
                minValue = 50
            }
        }
    }
}
