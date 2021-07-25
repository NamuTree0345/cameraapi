plugins {
}

group = "org.example"
version = "1.0.0"

repositories {
    mavenCentral()
    mavenLocal()
}

dependencies {
    compileOnly("io.papermc.paper:paper:1.17.1-R0.1-SNAPSHOT")
    implementation(project(":API"))
}
