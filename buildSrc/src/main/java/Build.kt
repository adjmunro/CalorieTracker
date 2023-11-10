import org.gradle.api.JavaVersion

object Build {
    private const val androidBuildToolsVersion = "8.1.3"
    const val androidBuildTools = "com.android.tools.build:gradle:$androidBuildToolsVersion"

    const val kotlinGradlePlugin = "org.jetbrains.kotlin:kotlin-gradle-plugin:${Kotlin.version}"

    private const val hiltAndroidGradlePluginVersion = "2.48"
    const val hiltAndroidGradlePlugin =
        "com.google.dagger:hilt-android-gradle-plugin:$hiltAndroidGradlePluginVersion"

    // Temp
    const val kspGradlePlugin = "${Ksp.kotlinSymbolProcessing}:symbol-processing-api:${Ksp.version}"

    @JvmField
    final val javaCompatibilityVersion: JavaVersion = JavaVersion.VERSION_17
    const val jvmTarget: String = "17"
}
