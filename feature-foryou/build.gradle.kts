plugins {
    id("nowinandroid.android.library")
    id("nowinandroid.android.feature")
    id("nowinandroid.android.library.compose")
    id("nowinandroid.android.library.jacoco")
    id("dagger.hilt.android.plugin")
    id("nowinandroid.spotless")
}

dependencies {
    implementation(libs.kotlinx.datetime)

    implementation(libs.androidx.compose.material3.windowSizeClass)

    implementation(libs.accompanist.flowlayout)
}
