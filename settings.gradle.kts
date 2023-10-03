pluginManagement {
    includeBuild("build-logic")

    repositories {
        google()
        gradlePluginPortal()
    }
}
dependencyResolutionManagement {
    repositories {
        google()
        mavenCentral()
    }
}

rootProject.name = "Foodie"
include(":app")

include(":features:cart")
include(":features:dishes")
include(":features:categories")

include(":services:data")
include(":services:network")
include(":services:location")
include(":services:navigation")
include(":services:compose")
include(":services:preference")

include(":core:common")
include(":coreui:theme")
include(":coreui:presentation")
