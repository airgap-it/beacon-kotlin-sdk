object Project {
    const val group = "it.airgap.beaconsdk"
    const val version = "3.0.0-beta01"
}

object Version {
    const val kotlin = "1.7.10"

    const val kotlinSerialization = "1.3.1"

    const val coroutines = "1.5.1"

    const val ktor = "1.6.2"

    const val lazySodium = "5.1.1"
    const val jna = "5.9.0"

    const val junit = "4.13.2"
    const val mockk = "1.12.0"

    const val bouncyCastle = "1.70"
}

object Dependencies {
    const val kotlinStdlib = "org.jetbrains.kotlin:kotlin-stdlib:${Version.kotlin}"
    const val kotlinReflection = "org.jetbrains.kotlin:kotlin-reflect:${Version.kotlin}"

    const val kotlinxSerializationJson = "org.jetbrains.kotlinx:kotlinx-serialization-json:${Version.kotlinSerialization}"
    const val kotlinxCoroutines = "org.jetbrains.kotlinx:kotlinx-coroutines-core:${Version.coroutines}"

    const val ktorOkHttp = "io.ktor:ktor-client-okhttp:${Version.ktor}"
    const val ktorJson = "io.ktor:ktor-client-json:${Version.ktor}"
    const val ktorSerializationJvm = "io.ktor:ktor-client-serialization-jvm:${Version.ktor}"
    const val ktorLoggingJvm = "io.ktor:ktor-client-logging-jvm:${Version.ktor}"

    const val lazySodium = "com.goterl:lazysodium-java:${Version.lazySodium}"
    const val jna = "net.java.dev.jna:jna:${Version.jna}"
}

object TestDependencies {
    const val kotlinTest = "org.jetbrains.kotlin:kotlin-test:${Version.kotlin}"
    const val kotlinxCoroutinesTest = "org.jetbrains.kotlinx:kotlinx-coroutines-test:${Version.coroutines}"

    const val junit = "junit:junit:${Version.junit}"
    const val mockk = "io.mockk:mockk:${Version.mockk}"

    const val bouncyCastle = "org.bouncycastle:bcprov-jdk15on:${Version.bouncyCastle}"
}