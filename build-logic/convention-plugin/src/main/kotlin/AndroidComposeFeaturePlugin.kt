
import com.android.build.api.dsl.LibraryExtension
import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure
import ru.alexeyoss.foodie.buildlogic.config.AppConfig

open class AndroidComposeFeaturePlugin : AndroidLibraryPlugin() {
    override fun apply(project: Project) {
        super.apply(project)
        project.run {
            applyConfiguration()
        }
    }
    private fun Project.applyConfiguration() = extensions.configure<LibraryExtension> {
        buildFeatures {
            viewBinding = true
            compose = true
        }
        composeOptions {
            kotlinCompilerExtensionVersion = AppConfig.KOTLIN_COMPILER_EXTENSION_VERSION
        }
    }
}
