plugins {
    alias(libs.plugins.runstory.jvm.library)
}

dependencies {
    implementation(projects.core.domain)
}