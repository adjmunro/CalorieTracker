plugins {
    `android-library`
    `kotlin-android`
}

apply(from = "$rootDir/base-module.gradle")

android.namespace = "nz.adjmunro.tracker.domain"

dependencies {
    implementation(project(Modules.core))
    implementation(Coroutines.coroutines)
}
