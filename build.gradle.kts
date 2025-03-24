plugins {
    id("java")
    id("maven-publish")
}

group = "com.github.Pritam11638"
version = "v1.21-1.0.0-BETA.2"

repositories {
    mavenCentral()
    maven {
        url = uri("https://repo.papermc.io/repository/maven-public/")
    }
}

dependencies {
    compileOnly("io.papermc.paper:paper-api:1.21-R0.1-SNAPSHOT")
}

java {
    toolchain.languageVersion.set(JavaLanguageVersion.of(21))
}

publishing {
    publications {
        create<MavenPublication>("maven") {
            from(components["java"])
            groupId = group.toString()
            artifactId = rootProject.name
            version = version
        }
    }
    repositories {
        maven {
            url = uri("https://jitpack.io")
        }
    }
}