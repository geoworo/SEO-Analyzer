plugins {
    id("java")
    id("checkstyle")
    id("application")
    id("io.freefair.lombok") version "8.3"
    id("com.github.johnrengelman.shadow") version "8.1.1"
}

group = "hexlet.code"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

application {
    mainClass.set("hexlet.code.App")
}

dependencies {
    implementation("org.postgresql:postgresql:42.5.4")
    implementation("gg.jte:jte:3.1.6")
    implementation("com.h2database:h2:2.2.220")
    implementation("io.javalin:javalin:5.6.1")
    implementation("org.slf4j:slf4j-simple:2.0.7")
    implementation("io.javalin:javalin-rendering:5.6.0")
    implementation("gg.jte:jte:3.0.1")
    implementation("com.zaxxer:HikariCP:5.0.1")
}

tasks.test {
    useJUnitPlatform()
}