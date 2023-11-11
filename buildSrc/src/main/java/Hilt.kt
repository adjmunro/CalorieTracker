object Hilt {
    /** Dagger Hilt Version */
    const val version: DependencyVersion = "2.48.1"

    /** Hilt Gradle Plugin */
    const val gradlePlugin: ProjectPluginId = "com.google.dagger.hilt.android"

    /** Hilt Gradle Plugin */
    const val plugin: ModulePluginId = "dagger.hilt.android.plugin"

    /** **KSP:** Hilt Compiler Annotation Processor (Pure Kotlin/Java) */
    const val compiler: KspId = "com.google.dagger:hilt-compiler:$version"

    /** Hilt Android */
    const val android: ImplementationId = "com.google.dagger:hilt-android:$version"

    /** **KSP:** Hilt *Android* Compiler Annotation Processor */
    const val androidCompiler: KspId = "com.google.dagger:hilt-android-compiler:$version"

    /** Hilt Android Testing */
    const val androidTest: ImplementationId = "com.google.dagger:hilt-android-testing:$version"

    object Androidx {
        /** Hilt AndroidX Extensions Version */
        const val version: DependencyVersion = "1.1.0"
        const val common: ImplementationId = "androidx.hilt:hilt-common:$version"
        const val compiler: ImplementationId = "androidx.hilt:hilt-compiler:$version" // or ksp?
//        const val lifecycle: ImplementationId = "androidx.hilt:hilt-lifecycle:$version"
        const val navigationCompose: ImplementationId = "androidx.hilt:hilt-navigation-compose:$version"
    }

    const val navigationCompose: ImplementationId = "androidx.hilt:hilt-navigation-compose:1.1.0"
}
