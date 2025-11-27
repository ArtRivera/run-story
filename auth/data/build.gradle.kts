plugins {
    alias(libs.plugins.runstory.android.library)
    alias(libs.plugins.runstory.jvm.ktor)
}

android {
    namespace = "com.artrivera.auth.data"
}

dependencies {

    implementation(libs.bundles.koin)
    implementation(projects.core.domain)
    implementation(projects.core.presentation.ui)
    implementation(projects.auth.domain)
    implementation(projects.core.data)
}