
import com.android.build.api.dsl.LibraryExtension
import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure

open class AndroidFeaturePlugin : AndroidLibraryPlugin() {
    override fun apply(project: Project) {
        super.apply(project)
        project.run {
            applyConfiguration()
        }
    }
    private fun Project.applyConfiguration() = extensions.configure<LibraryExtension> {
        buildFeatures {
            viewBinding = true
        }
    }
}
