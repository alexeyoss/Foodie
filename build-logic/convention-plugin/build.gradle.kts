plugins {
    `kotlin-dsl`
}

kotlin {
    jvmToolchain(17)
}

gradlePlugin {
    plugins {
        register("androidApplicationPlugin") {
            id = "foodie.android.application"
            implementationClass = "AndroidAppPlugin"
        }

        register("androidLibraryPlugin") {
            id = "foodie.android.library"
            implementationClass = "AndroidLibraryPlugin"
        }

        register("kotlinJvmLibraryPlugin") {
            id = "foodie.kotlin.jvm"
            implementationClass = "KotlinJvmLibraryPlugin"
        }

        register("androidFeaturePlugin") {
            id = "foodie.android.feature"
            implementationClass = "AndroidFeaturePlugin"
        }

        register("androidComposeFeaturePlugin") {
            id = "foodie.android.feature.compose"
            implementationClass = "AndroidComposeFeaturePlugin"
        }
    }
}

dependencies {
    implementation(libs.android.gradle.plugin)
    implementation(libs.kotlin.gradle.plugin)
}
