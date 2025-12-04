group = "KAI_OOP.lab2"
version = "1.0"

plugins {
    kotlin("jvm")
    application
}

application {
    mainClass.set("MainKt")
}

dependencies {
    implementation(kotlin("stdlib-jdk8"))
}

sourceSets {
    named("main") {
        java.setSrcDirs(listOf("."))
    }
}

java {
    sourceCompatibility = JavaVersion.VERSION_11
    targetCompatibility = JavaVersion.VERSION_11
}

tasks.withType<org.jetbrains.kotlin.gradle.tasks.KotlinCompile> {
    kotlinOptions.jvmTarget = "11"
}