import org.jetbrains.kotlin.gradle.plugin.mpp.pm20.util.libsDirectory
import org.jetbrains.kotlin.utils.addToStdlib.trimToSize

plugins {
    `java-library`
    id("com.github.johnrengelman.shadow") version "8.1.1"
    kotlin("jvm") version "1.9.22"
}

group = "me.onlyjordon"
version = "1.0.0"
description = ""

java {
    toolchain.languageVersion.set(JavaLanguageVersion.of(17))
}

sourceSets.main {
    java.srcDirs("src/main/java", "src/main/kotlin")
}

repositories {
    mavenCentral()
    maven(url = "https://repo.dmulloy2.net/repository/public/")
    maven(url = "https://jitpack.io")
    maven(url = "https://hub.spigotmc.org/nexus/content/repositories/snapshots/")
    maven(url = "https://repo.inventivetalent.org/repository/public/")

}

dependencies {
    compileOnly("org.spigotmc:spigot-api:1.20.4-R0.1-SNAPSHOT")
    compileOnly("com.github.jordoncodes:nicknamer-api:v1.2.0")
    implementation("org.mineskin:java-client:1.2.2-SNAPSHOT")
    implementation(kotlin("stdlib-jdk8"))
}

tasks {

    jar {
        duplicatesStrategy = DuplicatesStrategy.EXCLUDE
        dependsOn("shadowJar")
    }

    shadowJar {
        project.configurations.implementation.get().isCanBeResolved = true
        configurations = listOf(project.configurations.implementation.get())
        destinationDirectory.set(file("server/plugins"))
    }

    build {
        dependsOn(shadowJar)
    }

    compileJava {
        options.encoding = Charsets.UTF_8.name()
        // Set the release flag. This configures what version bytecode the compiler will emit, as well as what JDK APIs are usable.
        // See https://openjdk.java.net/jeps/247 for more information.
        options.release.set(17)
    }
    javadoc {
        options.encoding = Charsets.UTF_8.name()
    }
}