plugins {
    id("nowinandroid.android.library")
    kotlin("kapt")
    id("dagger.hilt.android.plugin")
    id("nowinandroid.spotless")
}

dependencies {
    api(project(":core-datastore"))
    implementation(project(":core-testing"))

    api(libs.androidx.dataStore.core)

    implementation(libs.hilt.android)
    kapt(libs.hilt.compiler)
    kaptAndroidTest(libs.hilt.compiler)
}
