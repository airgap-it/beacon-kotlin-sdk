plugins {
    kotlin("plugin.serialization") version Version.kotlin
}

dependencies {
    implementation(Dependencies.kotlinStdlib)

    // Reflection
    implementation(Dependencies.kotlinReflection)

    // Serialization
    implementation(Dependencies.kotlinxSerializationJson)

    // Coroutines
    implementation(Dependencies.kotlinxCoroutines)

    // Ktor
    implementation(Dependencies.ktorOkHttp)
    implementation(Dependencies.ktorJson)
    implementation(Dependencies.ktorSerializationJvm)
    implementation(Dependencies.ktorLoggingJvm)

    // Lazy Sodium
    implementation(Dependencies.lazySodium)
    implementation(Dependencies.jna)

    // Test
    testImplementation(TestDependencies.kotlinTest)
    testImplementation(TestDependencies.kotlinxCoroutinesTest)

    testImplementation(TestDependencies.junit)
    testImplementation(TestDependencies.mockk)
}