plugins {
    alias(libs.plugins.runstory.jvm.library)
}

// Check if it is really needed
dependencies {
    implementation(libs.kotlinx.coroutines.core)
}
