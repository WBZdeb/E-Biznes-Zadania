
plugins {
    kotlin("jvm") version "2.1.0"
    kotlin("plugin.serialization") version "1.8.0"
    application
}

group = "com.example"
version = "0.0.1"

application {
    mainClass.set("ApplicationKt")
}

repositories {
    mavenCentral()
    maven("https://jitpack.io")
}

dependencies {
    implementation("io.ktor:ktor-server-core:2.3.2")
    implementation("io.ktor:ktor-server-netty:2.3.2")
    implementation("ch.qos.logback:logback-classic:1.4.11")
    testImplementation("io.ktor:ktor-server-test-host:2.3.2")
    testImplementation("org.jetbrains.kotlin:kotlin-test-junit:1.8.0")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.7.3")
    implementation("io.ktor:ktor-server-host-common:2.3.2")
    implementation("io.ktor:ktor-server-call-logging:2.3.2")
    implementation("io.ktor:ktor-server-status-pages:2.3.2")

    implementation("io.ktor:ktor-client-cio:2.3.2")
    implementation("io.ktor:ktor-client-content-negotiation:2.3.2")
    implementation("io.ktor:ktor-serialization-kotlinx-json:2.3.2")
    implementation("net.dv8tion:JDA:5.0.0-beta.13")

    implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:1.8.0")
    implementation("com.fasterxml.jackson.dataformat:jackson-dataformat-yaml:2.14.1")
}

tasks.withType<org.jetbrains.kotlin.gradle.tasks.KotlinCompile> {
    kotlinOptions {
        jvmTarget = "17"
    }
}

java {
    toolchain {
        languageVersion.set(JavaLanguageVersion.of(17))
    }
}
