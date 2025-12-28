plugins {
    kotlin("jvm")
    application
}

application {
    mainClass.set("MainKt")
}

dependencies {
    implementation(project(":domain"))
    implementation(project(":business-logic"))
    implementation(project(":data-access"))
}

sourceSets {
    named("main") {
        java.setSrcDirs(listOf("."))
    }
}

tasks.named<JavaExec>("run") {
    standardInput = System.`in`
}
