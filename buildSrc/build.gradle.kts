plugins {
    `kotlin-dsl`
}

repositories {
    google()
    gradlePluginPortal()
    mavenCentral()
}

dependencies {
    implementation("org.jetbrains.kotlin:kotlin-gradle-plugin:1.9.10")
    implementation("com.android.tools.build:gradle:8.1.3")

    // HILT BUG: https://github.com/google/dagger/issues/3068#issuecomment-999118496
    implementation("com.squareup:javapoet:1.13.0")
}
