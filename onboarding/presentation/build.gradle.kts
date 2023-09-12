plugins {
    `android-library`
    `kotlin-android`
}

apply(from = "$rootDir/compose-module.gradle")

android.namespace = "nz.adjmunro.presentation"

dependencies {
    implementation(project(Modules.core))
    implementation(project(Modules.onboardingDomain))
}
