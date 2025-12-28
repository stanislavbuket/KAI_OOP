plugins {
    kotlin("jvm")
}

dependencies {
    implementation(project(":domain"))
    implementation(project(":data-access"))
}

sourceSets {
    named("main") {
        java.setSrcDirs(listOf("."))
    }
}
