import com.artrivera.buildlogic.convention.addUiDependencies
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.dependencies

class AndroidFeatureUiConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        target.run {
            pluginManager.run {
                apply("runstory.android.library.compose")
            }

            dependencies {
                addUiDependencies(target)
            }
        }
    }
}