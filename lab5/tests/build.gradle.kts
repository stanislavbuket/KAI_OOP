plugins {
    kotlin("jvm")
}

dependencies {
    implementation(project(":domain"))
    implementation(project(":business-logic"))
    implementation(project(":data-access"))
    
    // JUnit 5
    testImplementation(platform("org.junit:junit-bom:5.10.0"))
    testImplementation("org.junit.jupiter:junit-jupiter")
    
    // MockK
    testImplementation("io.mockk:mockk:1.13.8")
}

sourceSets {
    named("test") {
        java.setSrcDirs(listOf("."))
    }
}

tasks.test {
    useJUnitPlatform()
}
