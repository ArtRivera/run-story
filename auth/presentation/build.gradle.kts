plugins {
    alias(libs.plugins.runstory.android.feature.ui)
}

android {
    namespace = "com.artrivera.auth.presentation"
}

dependencies {

    implementation(libs.bundles.koin)
    implementation(projects.core.domain)
    implementation(projects.auth.domain)
}