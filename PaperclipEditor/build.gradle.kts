plugins {
    kotlin("jvm") version "1.7.21"
}

group = "com.paperclip.editor"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {

    api(project(":PaperclipEngine"))

    implementation("io.github.spair:imgui-java-binding")
    implementation("io.github.spair:imgui-java-lwjgl3")

    // TODO: Implement the others based on os
    // imgui-java-natives-windows
    // imgui-java-natives-linux
    // imgui-java-natives-macos
    implementation("io.github.spair:imgui-java-app:1.86.5")
}

tasks.getByName<Test>("test") {
    useJUnitPlatform()
}

tasks.withType<org.jetbrains.kotlin.gradle.tasks.KotlinCompile> {
    kotlinOptions.jvmTarget = "1.8"
}