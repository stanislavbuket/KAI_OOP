plugins {
    java
}

group = "KAI_OOP.coursework"
version = "1.0"

java {
    sourceCompatibility = JavaVersion.VERSION_11
    targetCompatibility = JavaVersion.VERSION_11
}

sourceSets {
    named("main") {
        java.setSrcDirs(listOf("."))
    }
}