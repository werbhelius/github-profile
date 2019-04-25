val kotlinVersion = "1.3.30"
val ktorVersion = "1.1.4"
val logbackVersion= "1.2.1"
val konfig = "1.6.10.0"

plugins {
    application
    kotlin("jvm") version "1.3.30"
}

application {
    applicationName = "github-profile"
    mainClassName = "io.ktor.server.netty.EngineMain"
    group = "github-profile"
    version = "0.1.0"
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
