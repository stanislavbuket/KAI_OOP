group = "KAI_OOP.lab1"
version = "1.0"

plugins {
    kotlin("jvm")
}

java {
    sourceCompatibility = JavaVersion.VERSION_11
    targetCompatibility = JavaVersion.VERSION_11
}

tasks.withType<org.jetbrains.kotlin.gradle.tasks.KotlinCompile> {
    kotlinOptions.jvmTarget = "11"
}

sourceSets {
    named("main") {
        java.setSrcDirs(listOf("."))
    }
}
