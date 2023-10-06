import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.plugins.JavaPluginExtension
import org.gradle.kotlin.dsl.configure
import ru.alexeyoss.foodie.buildlogic.config.AppConfig
import ru.alexeyoss.foodie.buildlogic.config.Plugins
import ru.alexeyoss.foodie.buildlogic.extensions.applyPlugin

/**
 * Плагин для Kotlin / JVM модулей, где можно обойтись без зависимостей на Android.
 */
class KotlinJvmLibraryPlugin : Plugin<Project> {
    override fun apply(project: Project) {
        project.run {
            applyPlugin(name = Plugins.JAVA_LIBRARY)
            applyPlugin(name = Plugins.KOTLIN_JVM)

            extensions.configure<JavaPluginExtension> {
                sourceCompatibility = AppConfig.JVM_TARGET
                targetCompatibility = AppConfig.JVM_TARGET
            }
        }
    }
}
