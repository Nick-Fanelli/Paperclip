plugins {
    kotlin("jvm") version "1.7.21"
}

val jomlVersion = "1.10.5"

group = "com.paperclip.internal.samples"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {

    implementation("org.joml", "joml", jomlVersion)

    implementation(project(":PaperclipEngine"))
}

tasks.withType<org.jetbrains.kotlin.gradle.tasks.KotlinCompile> {
    kotlinOptions.jvmTarget = "1.8"
}