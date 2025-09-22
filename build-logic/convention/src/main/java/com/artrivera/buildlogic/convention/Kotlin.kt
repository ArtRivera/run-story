package com.artrivera.buildlogic.convention

import com.android.build.api.dsl.CommonExtension
import org.gradle.api.Project
import org.gradle.kotlin.dsl.dependencies
import org.gradle.kotlin.dsl.withType
import org.jetbrains.kotlin.gradle.dsl.JvmTarget
import org.jetbrains.kotlin.gradle.tasks.KotlinJvmCompile


internal fun Project.configureKotlinAndroid(
    commonExtension: CommonExtension<*, *, *, *, *, *>,
) {
    commonExtension.run {
        compileSdk = libs.findVersion("projectCompileSdkVersion").get().toString().toInt()
        defaultConfig {
            minSdk = libs.findVersion("projectMinSdkVersion").get().toString().toInt()
        }
        compileOptions {
            isCoreLibraryDesugaringEnabled = true
            sourceCompatibility = org.gradle.api.JavaVersion.VERSION_11
            targetCompatibility = org.gradle.api.JavaVersion.VERSION_11
        }

        configureKotlin()

        dependencies {
            "coreLibraryDesugaring"(libs.findLibrary("desugar.jdk.libs").get())
        }
    }
}

private fun Project.configureKotlin() {

    tasks.withType<KotlinJvmCompile>().configureEach {
        compilerOptions {
            jvmTarget.set(JvmTarget.JVM_11)
        }
    }
//
//    tasks.withType<KotlinCompile>().configureEach {
//
//        kotlinOptions{
//            jvmTarget = JavaVersion.VERSION_11.toString()
//        }
//    }
}