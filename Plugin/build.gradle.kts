plugins {
    id("com.github.johnrengelman.shadow") version "7.0.0"
}

group = "org.example"
version = "1.0.0"

repositories {
    mavenCentral()
    maven { url = uri("https://papermc.io/repo/repository/maven-public/") }
}

dependencies {
    compileOnly("io.papermc.paper:paper-api:1.17.1-R0.1-SNAPSHOT")
    implementation("com.google.code.gson:gson:2.8.7")
    implementation(project(":API"))

    implementation(project(":1_17_R1"))
}
