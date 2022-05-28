plugins {
    id("nowinandroid.android.library")
    kotlin("kapt")
    id("dagger.hilt.android.plugin")
    id("nowinandroid.spotless")
}

dependencies {
    api(project(":core-data"))
    implementation(project(":core-testing"))

    implementation(libs.hilt.android)
    kapt(libs.hilt.compiler)
    kaptAndroidTest(libs.hilt.compiler)
}
