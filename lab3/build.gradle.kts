plugins {
    kotlin("jvm") version "1.9.22" apply false
}

allprojects {
    repositories {
        mavenCentral()
    }
}

tasks.register("run") {
    group = "application"
    description = "Runs the Lab 3 application."
    dependsOn(project(":app").tasks.named("run"))
}