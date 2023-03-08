plugins {
    kotlin("jvm") version "1.8.10"
    id("jacoco")
    java
}

java {
    toolchain {
        languageVersion.set(JavaLanguageVersion.of(17))
    }
}

group = "org.example"

repositories {
    mavenCentral()
}

dependencies {
    implementation(kotlin("stdlib-jdk8"))
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.6.4")
    testImplementation(kotlin("test"))
    testImplementation("org.junit.jupiter:junit-jupiter-api:5.7.0")
    testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine:5.7.0")
}

//tasks.named<Test>("test") {
tasks.test {
    useJUnitPlatform()

    testLogging {
        events("passed")
    }
}