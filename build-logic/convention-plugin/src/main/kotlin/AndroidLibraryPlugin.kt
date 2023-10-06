import com.android.build.api.dsl.LibraryExtension
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure
import ru.alexeyoss.foodie.buildlogic.config.AppConfig
import ru.alexeyoss.foodie.buildlogic.config.Plugins
import ru.alexeyoss.foodie.buildlogic.extensions.applyPlugin
import ru.alexeyoss.foodie.buildlogic.extensions.configureKotlin

open class AndroidLibraryPlugin : Plugin<Project> {
    override fun apply(project: Project) {
        project.run {
            applyPlugins()
            applyAppConfig()
        }
    }

    private fun Project.applyPlugins() {
        applyPlugin(name = Plugins.ANDROID_LIBRARY)
        applyPlugin(name = Plugins.KOTLIN_KAPT)
        applyPlugin(name = Plugins.KOTLIN_ANDROID)
        applyPlugin(name = Plugins.KOTLIN_PARCELIZE)
    }

    private fun Project.applyAppConfig() = extensions.configure<LibraryExtension> {
        compileSdk = AppConfig.COMPILE_SDK

        defaultConfig {
            minSdk = AppConfig.MIN_SDK
        }

        buildTypes {
            getByName("debug") {
                isMinifyEnabled = false
            }
            getByName("release") {
                isMinifyEnabled = AppConfig.MINIFY_RELEASE
                proguardFiles(
                    getDefaultProguardFile("proguard-android-optimize.txt"),
                    "proguard-rules.pro"
                )
            }
        }
        buildFeatures {
            buildConfig = true
        }
        configureKotlin(this)
    }
}
