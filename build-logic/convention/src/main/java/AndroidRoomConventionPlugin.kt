import androidx.room.gradle.RoomExtension
import com.artrivera.buildlogic.convention.DependencyType
import com.artrivera.buildlogic.convention.addDependency
import com.artrivera.buildlogic.convention.libs
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure
import org.gradle.kotlin.dsl.dependencies

class AndroidRoomConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        target.run {
            pluginManager.run {
                apply("androidx.room")
                apply("com.google.devtools.ksp")
            }

            extensions.configure<RoomExtension> {
                schemaDirectory("$projectDir/schemas")
            }

            dependencies {
                addDependency(DependencyType.IMPLEMENTATION, libs.findLibrary("room.runtime").get())
                addDependency(DependencyType.IMPLEMENTATION, libs.findLibrary("room.ktx").get())
                addDependency(DependencyType.KSP, libs.findLibrary("room.compiler").get())
            }
        }
    }
}