plugins {
    `kotlin-dsl`
}

group = "com.artrivera.runstory.buildlogic"

dependencies {
    compileOnly(libs.android.gradlePlugin)
    compileOnly(libs.android.tools.common)
    compileOnly(libs.kotlin.gradlePlugin)
    compileOnly(libs.ksp.gradlePlugin)
    compileOnly(libs.room.gradlePlugin)
}

gradlePlugin {
    plugins {
        register("android-application"){
            id = "runstory.android.application"
            implementationClass = "AndroidApplicationConventionPlugin"
        }
        register("android-application-compose"){
            id = "runstory.android.application.compose"
            implementationClass = "AndroidApplicationComposeConventionPlugin"
        }
        register("android-library"){
            id = "runstory.android.library"
            implementationClass = "AndroidLibraryConventionPlugin"
        }
        register("android-library-compose"){
            id = "runstory.android.library.compose"
            implementationClass = "AndroidLibraryComposeConventionPlugin"
        }
        register("android-feature-ui"){
            id = "runstory.android.feature.ui"
            implementationClass = "AndroidFeatureUiConventionPlugin"
        }

        register("android-room"){
            id = "runstory.android.room"
            implementationClass = "AndroidRoomConventionPlugin"
        }

        register("jvm-library"){
            id = "runstory.jvm.library"
            implementationClass = "JvmLibraryConventionPlugin"
        }
        register("jvm-ktor"){
            id = "runstory.jvm.ktor"
            implementationClass = "JvmKtorConventionPlugin"
        }
    }
}