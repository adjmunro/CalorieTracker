plugins {
    `android-library`
    `kotlin-android`
}

apply(from = "$rootDir/compose-module.gradle")

android.namespace = "nz.adjmunro.coreui"

dependencies {
    implementation(project(Modules.core))
}
