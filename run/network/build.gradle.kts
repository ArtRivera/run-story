plugins {
    alias(libs.plugins.runstory.android.library)
    alias(libs.plugins.runstory.jvm.ktor)
}

android {
    namespace = "com.artrivera.run.network"
}

dependencies {

   implementation(projects.core.domain)
//   implementation(projects.core.data)
}