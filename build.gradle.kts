/*
 * This file was generated by the Gradle 'init' task.
 *
 * This generated file contains a sample Java project to get you started.
 * For more details take a look at the Java Quickstart chapter in the Gradle
 * User Manual available at https://docs.gradle.org/6.4.1/userguide/tutorial_java_projects.html
 */

plugins {

    id("org.springframework.boot") version "2.3.1.RELEASE"

    id("io.spring.dependency-management") version "1.0.9.RELEASE"

    // Apply the java plugin to add support for Java
    java

    // Apply the application plugin to add support for building a CLI application.
    application
}

java{
    sourceCompatibility = JavaVersion.VERSION_13
    targetCompatibility = JavaVersion.VERSION_13
}

repositories {
    // Use jcenter for resolving dependencies.
    // You can declare any Maven/Ivy/file repository here.
    jcenter()
}

dependencies {
    // This dependency is used by the application.
    implementation("com.google.guava:guava:28.2-jre")

    // Spring
    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("org.springframework.boot:spring-boot-starter-websocket")
    implementation("org.springframework.boot:spring-boot-starter-webflux")

    // Gson
    implementation("com.google.code.gson:gson:2.8.6")

    // MongoDB
    implementation("org.mongodb:mongo-java-driver:3.12.5")

    // Use JUnit Jupiter API for testing.
    testImplementation("org.junit.jupiter:junit-jupiter-api:5.6.0")

    // Mockito
    testImplementation("org.mockito:mockito-core:3.3.3")

    // Use JUnit Jupiter Engine for testing.
    testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine:5.6.0")
}
ext{
    set("elasticsearch.version", "7.7.1")
}

application {
    // Define the main class for the application.
    mainClassName = "com.traderepublic.quotessystem.App"
}

val test by tasks.getting(Test::class) {
    // Use junit platform for unit tests
    useJUnitPlatform()
}


