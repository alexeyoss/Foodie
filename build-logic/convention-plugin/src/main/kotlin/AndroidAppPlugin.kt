
import com.android.build.gradle.internal.dsl.BaseAppModuleExtension
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure
import org.gradle.kotlin.dsl.dependencies
import ru.alexeyoss.foodie.buildlogic.config.AppConfig
import ru.alexeyoss.foodie.buildlogic.config.Plugins
import ru.alexeyoss.foodie.buildlogic.extensions.applyPlugin
import ru.alexeyoss.foodie.buildlogic.extensions.configureKotlin
import ru.alexeyoss.foodie.buildlogic.extensions.getVersionCatalog
import ru.alexeyoss.foodie.buildlogic.extensions.implementation
import ru.alexeyoss.foodie.buildlogic.extensions.kotlinOptions

/**
 * Convention plugin для настройки app модулей.
 */
open class AndroidAppPlugin : Plugin<Project> {

    override fun apply(project: Project) {
        project.run {
            applyPlugins()
            applyAppConfig()
        }
    }

    private fun Project.applyPlugins() {
        applyPlugin(name = Plugins.KOTLIN_KAPT)
        applyPlugin(name = Plugins.ANDROID_APPLICATION)
        applyPlugin(name = Plugins.KOTLIN_ANDROID)
        applyPlugin(name = Plugins.KOTLIN_PARCELIZE)
    }

    private fun Project.applyAppConfig() = extensions.configure<BaseAppModuleExtension> {
        namespace = AppConfig.APP_DOMAIN
        compileSdk = AppConfig.COMPILE_SDK

        defaultConfig {
            applicationId = AppConfig.APP_DOMAIN
            minSdk = AppConfig.MIN_SDK
            targetSdk = AppConfig.TARGET_SDK
            versionCode = AppConfig.VERSION_CODE
            versionName = AppConfig.VERSION_NAME

            vectorDrawables {
                useSupportLibrary = true
            }
        }

        buildTypes {
            debug {
                isMinifyEnabled = false
                manifestPlaceholders["appIcon"] = "@mipmap/ic_launcher_debug"
                applicationIdSuffix = ".beta"
            }
            release {
                manifestPlaceholders["appIcon"] = "@mipmap/ic_launcher_prod"
                isMinifyEnabled = true
            }
        }
        kotlinOptions {
            jvmTarget = AppConfig.JVM_TARGET.toString()
        }
        buildFeatures {
            viewBinding = true
            compose = true
            buildConfig = true
        }
        composeOptions {
            kotlinCompilerExtensionVersion = AppConfig.KOTLIN_COMPILER_EXTENSION_VERSION
        }
        packaging {
            resources {
                excludes += "/META-INF/{AL2.0,LGPL2.1}"
            }
        }
        configureKotlin(this)
        dependencies {
            val composeVersion = getVersionCatalog().findVersion("compose_bom")

            implementation(platform("androidx.compose:compose-bom:$composeVersion"))
            implementation("androidx.compose.runtime:runtime")
        }
    }
}
