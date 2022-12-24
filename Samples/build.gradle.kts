plugins {
    kotlin("jvm") version "1.7.21"
}

group = "com.paperclip.internal.samples"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {

    implementation(project(":PaperclipEngine"))
}

tasks.withType<org.jetbrains.kotlin.gradle.tasks.KotlinCompile> {
    kotlinOptions.jvmTarget = "1.8"
}