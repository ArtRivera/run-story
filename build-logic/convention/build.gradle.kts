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
    }
}