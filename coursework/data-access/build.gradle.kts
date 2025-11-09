plugins {
    java
}

group = "KAI_OOP.coursework"
version = "1.0"

dependencies {
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