import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

group = "io.kjson"
version = "0.3"

val kotlinVersion = "2.0.21"
val ktorVersion = "3.0.3"

plugins {
    kotlin("jvm") version "2.0.21"
    application
}

repositories {
    mavenLocal()
    mavenCentral()
}

kotlin {
    jvmToolchain(17)
}

dependencies {
    implementation(kotlin("stdlib-jdk8"))
    implementation("io.kjson:kjson-ktor:1.4")
    implementation("io.kjson:mustache-k:3.9")
    implementation("io.ktor:ktor-server-jvm:${ktorVersion}")
    implementation("io.ktor:ktor-server-core-jvm:${ktorVersion}")
    implementation("io.ktor:ktor-server-host-common-jvm:${ktorVersion}")
    implementation("io.ktor:ktor-server-netty-jvm:${ktorVersion}")
    implementation("io.ktor:ktor-client-core-jvm:${ktorVersion}")
    implementation("io.ktor:ktor-client-okhttp-jvm:${ktorVersion}")
    implementation("io.ktor:ktor-client-cio-jvm:${ktorVersion}")
    implementation("io.kstuff:log-front-kotlin:6.2")
    implementation("ch.qos.logback:logback-classic:1.3.15")

    testImplementation(kotlin("test"))
    testImplementation(kotlin("test-junit"))
}

application {
    mainClass.set("io.kjson.demo1.MainKt")
}
