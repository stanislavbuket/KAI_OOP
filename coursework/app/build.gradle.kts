plugins {
    java
    application
}

group = "KAI_OOP.coursework"
version = "1.0"

application {
    mainClass.set("Main")
}

tasks.getByName<JavaExec>("run") {
    standardInput = System.`in`
}

dependencies {
    implementation(project(":data-access"))
    implementation(project(":domain"))
    implementation("com.google.code.gson:gson:2.10.1")
}

java {
    sourceCompatibility = JavaVersion.VERSION_11
    targetCompatibility = JavaVersion.VERSION_11
}

sourceSets {
    named("main") {
        java.setSrcDirs(listOf("."))
    }
}