package com.artrivera.buildlogic.convention

import com.android.build.api.dsl.ApplicationExtension
import com.android.build.api.dsl.BuildType
import com.android.build.api.dsl.CommonExtension
import com.android.build.api.dsl.LibraryExtension
import com.android.build.gradle.internal.cxx.configure.gradleLocalProperties
import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure

internal fun Project.configureBuildTypes(
    commonExtension: CommonExtension<*, *, *, *, *, *>,
    extensionType: ExtensionType
) {
    commonExtension.apply {
        buildFeatures {
            buildConfig = true
        }
        val apiKey = gradleLocalProperties(rootDir, providers).getProperty("API_KEY")
        when (extensionType) {
            ExtensionType.APPLICATION -> extensions.configure<ApplicationExtension> {
                buildTypes {
                    debug {
                        // Assuming you want to configure debug for applications as well
                        apiKey?.let { it ->
                            configureDebugBuild(it)
                        }
                    }

                    release {
                        apiKey?.let { it ->
                            configureReleaseBuild(this@configure, it)
                        }
                    }

                }
            }

            ExtensionType.LIBRARY -> extensions.configure<LibraryExtension> {
                buildTypes {

                    debug {
                        // Assuming you want to configure debug for applications as well
                        apiKey?.let { it ->
                            configureDebugBuild(it)
                        }
                    }

                    release {
                        apiKey?.let { it ->
                            configureReleaseBuild(this@configure, it)
                        }
                    }

                }
            }
        }
    }
}

private fun BuildType.configureDebugBuild(apiKey: String) {
    buildConfigField("String", "API_KEY", "\"$apiKey\"")
    buildConfigField("String", "BASE_URL", "\" https ://runique.pl-coding.com:8080\"")
}

private fun BuildType.configureReleaseBuild(
    commonExtension: CommonExtension<*, *, *, *, *, *>,
    apiKey: String
) {
    buildConfigField("String", "API_KEY", "\"$apiKey\"")
    buildConfigField("String", "BASE_URL", "\" https ://runique.pl-coding.com:8080\"")
    isMinifyEnabled = true
    proguardFiles(
        commonExtension.getDefaultProguardFile("proguard-android-optimize.txt"),
        "proguard-rules.pro"
    )
}
