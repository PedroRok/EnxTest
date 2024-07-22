import com.github.jengelman.gradle.plugins.shadow.tasks.ShadowJar

plugins {
    id("java")
    id("io.papermc.paperweight.userdev") version "1.7.1"
    id("io.github.goooler.shadow") version "8.1.8"
}

group = "com.pedrorok"
version = "1.0"

java {
    toolchain.languageVersion.set(JavaLanguageVersion.of(21))
}

repositories {
    maven {
        url = uri("https://repo.papermc.io/repository/maven-public/")
    }
}

dependencies {
    compileOnly("org.projectlombok:lombok:1.18.34")
    annotationProcessor("org.projectlombok:lombok:1.18.34")

    paperweight.paperDevBundle("1.21-R0.1-SNAPSHOT")

    implementation("com.zaxxer:HikariCP:5.1.0")
    implementation("org.mariadb.jdbc:mariadb-java-client:2.7.4")
}

tasks {
    compileJava {
        options.release.set(21)
        options.encoding = Charsets.UTF_8.name()
    }

    val shadow =
        named<ShadowJar>("shadowJar") {
            archiveBaseName.set("EnxTest")
            exclude {
                it?.file?.name?.startsWith("patched_") == true
            }
        }
    reobfJar {
        outputJar.set(layout.buildDirectory.file("libs/${project.name}-reobf.jar"))
    }

    "build" {
        dependsOn(shadow)
        dependsOn(reobfJar)
    }
}


