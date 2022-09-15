import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    kotlin("jvm") version Version.kotlin
    `maven-publish`
    id("org.jetbrains.dokka") version Version.kotlin
}

allprojects {
    repositories {
        mavenCentral()
    }

    tasks.register<Jar>("fatJar") {
        archiveClassifier.set("fat")
        duplicatesStrategy = DuplicatesStrategy.EXCLUDE

        from(sourceSets.main.get().output)

        dependsOn(configurations.runtimeClasspath)
        from({
            configurations.runtimeClasspath.get().filter { it.name.endsWith("jar") }.map { zipTree(it) }
        })
    }
}

subprojects {
    apply(plugin = "kotlin")
    apply(plugin = "maven-publish")
    apply(plugin = "org.jetbrains.dokka")

    group = Project.group
    version = Project.version

    java {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }

    kotlin {
        explicitApiWarning()
    }

    tasks.withType<KotlinCompile>().configureEach {
        kotlinOptions {
            jvmTarget = "1.8"
            freeCompilerArgs = freeCompilerArgs + "-opt-in=kotlin.RequiresOptIn" + "-opt-in=it.airgap.beaconsdk.core.internal.annotation.InternalBeaconApi"
        }
    }

    tasks.assemble {
        dependsOn("fatJar")
    }

    publishing {
        publications {
            create<MavenPublication>("maven") {
                from(components.getByName("java"))

                artifactId = this@subprojects.name
            }
        }
    }
}

java {
    sourceCompatibility = JavaVersion.VERSION_1_8
    targetCompatibility = JavaVersion.VERSION_1_8
}

tasks.assemble {
    dependsOn("fatJar")
}

dependencies {
    subprojects.forEach {
        implementation(project(it.path))
    }

    testImplementation(kotlin("test"))
}