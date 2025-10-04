plugins {
    alias(libs.plugins.runstory.android.library)
    alias(libs.plugins.runstory.jvm.ktor)
}

android {
    namespace = "com.artrivera.auth.data"
}

dependencies {
//    implementation(projects.core.data)
    implementation(projects.core.domain)
    implementation(projects.auth.domain)
}