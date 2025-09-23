import com.android.build.api.dsl.LibraryExtension
import com.artrivera.buildlogic.convention.ExtensionType
import com.artrivera.buildlogic.convention.configureBuildTypes
import com.artrivera.buildlogic.convention.configureKotlinAndroid
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure
import org.gradle.kotlin.dsl.dependencies
import org.gradle.kotlin.dsl.kotlin

class AndroidLibraryConventionPlugin: Plugin<Project> {
    override fun apply(target: Project) {
        target.run {
            pluginManager.run {
                apply("com.android.library")
                apply("org.jetbrains.kotlin.android")
            }

            extensions.configure<LibraryExtension>(){
                configureKotlinAndroid(this)
                configureBuildTypes(this, ExtensionType.LIBRARY)

                defaultConfig {
                    testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
                    consumerProguardFiles("consumer-rules.pro")
                }
            }



            dependencies {
                add("testImplementation", kotlin("test"))
            }
        }
    }
}