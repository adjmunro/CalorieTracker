pluginManagement {
    repositories {
        google()
        gradlePluginPortal()
        mavenCentral()
    }
}
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        gradlePluginPortal()
        mavenCentral()
    }
}

rootProject.name = "CaloryTracker"
include(":app")
include(":core")
include(":core-ui")
include(":onboarding")
include(":onboarding:presentation")
include(":onboarding:domain")
include(":tracker")
include(":tracker:data")
include(":tracker:domain")
include(":tracker:presentation")
