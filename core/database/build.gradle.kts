plugins {
    alias(libs.plugins.runstory.android.library)
    alias(libs.plugins.runstory.android.room)
}

android {
    namespace = "com.artrivera.core.database"
}

dependencies {

    implementation(libs.mongodb.bson)
    implementation(projects.core.domain)
}