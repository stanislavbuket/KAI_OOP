plugins {
    kotlin("jvm")
}

dependencies {
    implementation(project(":domain"))
}

sourceSets {
    named("main") {
        java.setSrcDirs(listOf("."))
    }
}
