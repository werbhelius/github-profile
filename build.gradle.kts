import com.github.jengelman.gradle.plugins.shadow.tasks.ShadowJar
import proguard.gradle.ProGuardTask

buildscript {
    repositories {
        maven("https://plugins.gradle.org/m2")
    }
    dependencies {
        classpath("net.sf.proguard:proguard-gradle:6.1.0")
    }
}

plugins {
    application
    kotlin("jvm") version "1.3.30"
    id("com.github.johnrengelman.shadow") version "4.0.2"
}

application {
    applicationName = "github-profile"
    mainClassName = "io.ktor.server.netty.EngineMain"
    group = "github-profile"
    version = "1.0.0"
}

java.sourceSets {
    getByName("main").java.srcDirs("src")
    getByName("main").resources.srcDirs("resources")
    getByName("test").java.srcDirs("test")
}

repositories {
    mavenLocal()
    jcenter()
    maven { setUrl("https://kotlin.bintray.com/ktor") }
    maven { setUrl("https://kotlin.bintray.com/kotlinx") }

}

dependencies {

    val kotlinVersion = "1.3.30"
    val ktorVersion = "1.1.4"
    val logbackVersion = "1.2.1"
    val konfig = "1.6.10.0"

    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8:$kotlinVersion")
    implementation("io.ktor:ktor-server-netty:$ktorVersion")
    implementation("ch.qos.logback:logback-classic:$logbackVersion")
    implementation("io.ktor:ktor-client-core:$ktorVersion")
    implementation("io.ktor:ktor-client-core-jvm:$ktorVersion")
    implementation("io.ktor:ktor-client-okhttp:$ktorVersion")
    implementation("io.ktor:ktor-client-jackson:$ktorVersion")
    implementation("io.ktor:ktor-jackson:$ktorVersion")
    implementation("io.ktor:ktor-locations:$ktorVersion")
    implementation("com.natpryce:konfig:$konfig")
    implementation("io.ktor:ktor-freemarker:$ktorVersion")
    testImplementation("io.ktor:ktor-server-tests:$ktorVersion")
}

tasks.withType<ShadowJar> {
    baseName = application.applicationName
    version = "1.0.0"
    classifier = ""
}

task(name = "minimizedJar", type = ProGuardTask::class) {
    dependsOn("shadowJar")
    injars("build/libs/github-profile.jar")
    outjars("build/libs/github-profile.min.jar")
    libraryjars(System.getProperties()["java.home"].toString() + "/lib/rt.jar")
    printmapping("build/libs/github-profile.map")
    ignorewarnings()
    dontobfuscate()
    dontoptimize()
    dontwarn()
    configuration("proguard.pro")
}
