plugins {
    kotlin("plugin.serialization") version Version.kotlin
}

dependencies {
    implementation(project(":core"))

    implementation(Dependencies.kotlinStdlib)

    // Serialization
    implementation(Dependencies.kotlinxSerializationJson)

    // Test
    testImplementation(TestDependencies.kotlinTest)
    testImplementation(TestDependencies.kotlinxCoroutinesTest)

    testImplementation(TestDependencies.junit)
    testImplementation(TestDependencies.mockk)
}