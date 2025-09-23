import com.artrivera.buildlogic.convention.DependencyType
import com.artrivera.buildlogic.convention.addDependency
import com.artrivera.buildlogic.convention.configureKotlinJvm
import com.artrivera.buildlogic.convention.libs
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.dependencies

class JvmKtorConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        target.run {
            pluginManager.run {
                apply("org.jetbrains.kotlin.plugin.serialization")
            }

            configureKotlinJvm()

            dependencies {
                addDependency(DependencyType.IMPLEMENTATION, libs.findBundle("ktor").get())
            }

        }
    }
}