import org.gradle.api.JavaVersion

typealias KspId = String
typealias ProjectPluginId = String
typealias ModulePluginId = String
typealias ClasspathId = String
typealias DependencyVersion = String
typealias ImplementationId = String

object Build {
    private const val androidBuildToolsVersion = "8.1.3"
    const val androidBuildTools = "com.android.tools.build:gradle:$androidBuildToolsVersion"

    const val kotlinGradlePlugin = "org.jetbrains.kotlin:kotlin-gradle-plugin:${Kotlin.version}"

    // Temp
    const val kspGradlePlugin = "${Ksp.kotlinSymbolProcessing}:symbol-processing-api:${Ksp.version}"

    @JvmField
    final val javaCompatibilityVersion: JavaVersion = JavaVersion.VERSION_17
    const val jvmTarget: String = "17"
}
