// Top-level build file where you can add configuration options common to all sub-projects/modules.
buildscript {
    repositories {
        google()
        gradlePluginPortal()
        mavenCentral()
    }
    dependencies {
        classpath(Build.androidBuildTools)
        classpath(Build.kotlinGradlePlugin)
        classpath(Build.kspGradlePlugin)
        // NOTE: Do not place your application dependencies here; they belong
        // in the individual module build.gradle files
    }
}

//// TODO replace old buildscript with plugins block
plugins {
////    id("com.android.application") version "8.2.0-alpha03" apply false
//    id("org.jetbrains.kotlin.android") version Kotlin.version apply false
    id(Ksp.kotlinSymbolProcessing) version Ksp.version apply false
    id(Hilt.gradlePlugin) version Hilt.version apply false
}
//
//buildscript {
//    dependencies {
//        classpath(Build.hiltAndroidGradlePlugin)
//    }
//}

tasks.register<Delete>(name = "clean"){
    delete(rootProject.buildDir)
}
