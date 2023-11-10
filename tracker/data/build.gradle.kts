plugins {
    `android-library`
    `kotlin-android`
    id(Ksp.kotlinSymbolProcessing)
}

apply(from = "$rootDir/base-module.gradle")

android.namespace = "nz.adjmunro.tracker.data"

dependencies {
    implementation(project(Modules.core))
    implementation(project(Modules.trackerDomain))

    implementation(Retrofit.okHttp)
    implementation(Retrofit.retrofit)
    implementation(Retrofit.okHttpLoggingInterceptor)
    implementation(Retrofit.moshiConverter)

    ksp(Room.roomCompiler)
    implementation(Room.roomKtx)
    implementation(Room.roomRuntime)
}
