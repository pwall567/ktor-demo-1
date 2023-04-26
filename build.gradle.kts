import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

group = "io.kjson"
version = "0.1"

val kotlinVersion = "1.7.21"
val ktorVersion = "2.2.4"

plugins {
    kotlin("jvm") version("1.7.21")
    application
}

repositories {
    mavenLocal()
    mavenCentral()
}

tasks {
    withType<KotlinCompile> {
        kotlinOptions {
            languageVersion = "1.7"
            jvmTarget = "1.8"
        }
    }
}

dependencies {
    implementation(kotlin("stdlib-jdk8"))
    implementation("io.kjson:kjson-ktor:0.1")
    implementation("io.ktor:ktor-server-jvm:${ktorVersion}")
    implementation("io.ktor:ktor-server-core-jvm:${ktorVersion}")
    implementation("io.ktor:ktor-server-host-common-jvm:${ktorVersion}")
    implementation("io.ktor:ktor-server-netty-jvm:${ktorVersion}")
    implementation("io.ktor:ktor-client-core-jvm:${ktorVersion}")
    implementation("io.ktor:ktor-client-okhttp-jvm:${ktorVersion}")
    implementation("io.ktor:ktor-client-cio-jvm:${ktorVersion}")
    implementation("net.pwall.log:log-front-kotlin:5.1.2")

    testImplementation(kotlin("test"))
    testImplementation(kotlin("test-junit"))
}

application {
    mainClass.set("io.kjson.demo1.MainKt")
}
