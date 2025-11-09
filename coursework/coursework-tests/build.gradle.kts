plugins {
    java
}

group = "KAI_OOP.coursework"
version = "1.0"

repositories {
    mavenCentral()
}

dependencies {
    // Add dependencies on the other modules
    implementation(project(":domain"))
    implementation(project(":app"))

    // JUnit 5 dependencies
    testImplementation("org.junit.jupiter:junit-jupiter-api:5.9.1")
    testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine:5.9.1")
}

java {
    sourceCompatibility = JavaVersion.VERSION_11
    targetCompatibility = JavaVersion.VERSION_11
}

tasks.test {
    useJUnitPlatform()
    testLogging {
        events("passed", "skipped", "failed")
    }
}

// Since we are not using a standard directory structure,
// we need to tell Gradle where to find the test sources.
sourceSets {
    named("test") {
        java.setSrcDirs(listOf("."))
    }
}
