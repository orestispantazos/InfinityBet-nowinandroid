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
}
