val kotlin_version: String by project
val logback_version: String by project

plugins {
    kotlin("jvm") version "2.1.10"
    id("io.ktor.plugin") version "3.2.0"
    // --- ADD THIS LINE FOR KOTLINX SERIALIZATION PLUGIN ---
    id("org.jetbrains.kotlin.plugin.serialization") version "2.1.10" // Make sure this version matches your Kotlin JVM version
}

group = "com.ak1ena"
version = "0.0.1"

application {
    mainClass = "io.ktor.server.netty.EngineMain"
}

repositories {
    mavenCentral()
}

dependencies {
    implementation("io.ktor:ktor-server-core-jvm")
    implementation("io.ktor:ktor-server-netty")
    implementation("io.ktor:ktor-server-content-negotiation-jvm")

    // --- Ensure you're using the correct kotlinx.serialization-json dependency ---
    // The ktor-serialization-kotlinx-json-jvm dependency connects Ktor's content negotiation
    // with the actual kotlinx-serialization-json library.
    implementation("io.ktor:ktor-serialization-kotlinx-json-jvm")
    // You also need the core kotlinx-serialization-json library itself.
    // Use the latest stable version or one compatible with your Ktor/Kotlin setup.
    implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:1.7.1") // Using 1.7.1, check for the latest if available

    implementation("ch.qos.logback:logback-classic:$logback_version")
    implementation("io.ktor:ktor-server-core") // This might be redundant if you have -jvm version, but generally harmless
    implementation("io.ktor:ktor-server-config-yaml")
    testImplementation("io.ktor:ktor-server-test-host")
    testImplementation("org.jetbrains.kotlin:kotlin-test-junit:$kotlin_version")
}