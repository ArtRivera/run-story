plugins {
    alias(libs.plugins.runstory.android.library)
    alias(libs.plugins.runstory.jvm.ktor)
}

android {
    namespace = "com.artrivera.core.data"
}

dependencies {
    implementation(libs.timber)
    implementation(libs.bundles.koin)
    implementation(projects.core.domain)
    implementation(projects.core.database)
}