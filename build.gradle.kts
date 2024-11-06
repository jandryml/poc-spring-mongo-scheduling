import org.springframework.boot.gradle.plugin.SpringBootPlugin

plugins {
    kotlin("jvm") version "1.9.25"
    kotlin("plugin.spring") version "1.9.25"
    alias(libs.plugins.springBootPlugin)
    alias(libs.plugins.springDependencyManagement) 
}

group = "cz.jdr.poc.spring"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    implementation(platform(SpringBootPlugin.BOM_COORDINATES))
    implementation(libs.springBootStarter)
    implementation(libs.springBootStarterWeb)
    implementation(libs.springBootDevtools)
    implementation(libs.springBootStarterMongoDb)

    implementation(libs.kotlinReflect)
    implementation(libs.kotlinCoroutines)
    implementation(libs.kotlinArrow)
    
    testImplementation(libs.springBootStarterTest)
    
}

tasks.test {
    useJUnitPlatform()
}

kotlin {
    jvmToolchain(21)
}