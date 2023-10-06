
import com.android.build.api.dsl.LibraryExtension
import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure
import org.gradle.kotlin.dsl.dependencies
import ru.alexeyoss.foodie.buildlogic.config.AppConfig
import ru.alexeyoss.foodie.buildlogic.extensions.getVersionCatalog
import ru.alexeyoss.foodie.buildlogic.extensions.implementation

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

        dependencies {
            val composeVersion = getVersionCatalog().findVersion("compose_com")

            implementation(platform("androidx.compose:compose-bom:$composeVersion"))
            implementation("androidx.compose.runtime:runtime")
        }
    }
}
