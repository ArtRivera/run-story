plugins {
    alias(libs.plugins.runstory.android.library.compose)
}

android {
    namespace = "com.artrivera.core.presentation.design_system"
}

dependencies {

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.compose.ui)
    implementation(libs.androidx.compose.ui.graphics)
    implementation(libs.androidx.compose.ui.tooling.preview)
    api(libs.androidx.compose.material3)
}