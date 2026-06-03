plugins {
    kotlin("jvm") version "2.0.21"
    id("application")
}

group = "taskmanager"
version = "1.0-SNAPSHOT"

repositories { mavenCentral() }

dependencies {
    implementation("com.h2database:h2:2.2.224")
    implementation("org.mongodb:mongodb-driver-sync:5.3.1")
}

application { mainClass.set("app.MainKt") }

kotlin { jvmToolchain(21) }
