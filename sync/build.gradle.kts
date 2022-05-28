plugins {
    id("nowinandroid.android.library")
    id("nowinandroid.android.library.jacoco")
    kotlin("kapt")
    id("dagger.hilt.android.plugin")
    id("nowinandroid.spotless")
}

android {
    defaultConfig {
        testInstrumentationRunner = "com.google.samples.apps.nowinandroid.core.testing.NiaTestRunner"
    }
}

dependencies {
    implementation(project(":core-common"))
    implementation(project(":core-model"))
    implementation(project(":core-data"))
    implementation(project(":core-datastore"))

    implementation(libs.kotlinx.coroutines.android)

    implementation(libs.androidx.startup)
    implementation(libs.androidx.work.ktx)
    implementation(libs.hilt.ext.work)

    testImplementation(project(":core-testing"))
    androidTestImplementation(project(":core-testing"))

    implementation(libs.hilt.android)
    kapt(libs.hilt.compiler)
    kapt(libs.hilt.ext.compiler)

    androidTestImplementation(libs.androidx.work.testing)

    kaptAndroidTest(libs.hilt.compiler)
    kaptAndroidTest(libs.hilt.ext.compiler)
}
