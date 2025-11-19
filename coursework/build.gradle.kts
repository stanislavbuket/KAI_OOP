plugins {
    id("com.diffplug.spotless") version "6.25.0"
}

allprojects {
    repositories {
        mavenCentral()
    }
}

subprojects {
    apply(plugin = "com.diffplug.spotless")
    spotless {
        java {
            googleJavaFormat()
            trimTrailingWhitespace()
            endWithNewline()
        }
    }
}
