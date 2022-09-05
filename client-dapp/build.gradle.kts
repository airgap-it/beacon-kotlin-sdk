plugins {
    kotlin("plugin.serialization") version Version.kotlin
}

dependencies {
    implementation(project(":core"))

    implementation(Dependencies.kotlinStdlib)

    // Serialization
    implementation(Dependencies.kotlinxSerializationJson)

    // Coroutines
    implementation(Dependencies.kotlinxCoroutines)

    // Test
    testImplementation(TestDependencies.kotlinTest)
    testImplementation(TestDependencies.kotlinxCoroutinesTest)

    testImplementation(TestDependencies.junit)
    testImplementation(TestDependencies.mockk)
}