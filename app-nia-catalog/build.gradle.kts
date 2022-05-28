plugins {
    id("nowinandroid.android.application")
    id("nowinandroid.android.application.compose")
    id("nowinandroid.spotless")
}

android {
    defaultConfig {
        applicationId = "com.google.samples.apps.niacatalog"
    }

    packagingOptions {
        resources {
            excludes.add("/META-INF/{AL2.0,LGPL2.1}")
        }
    }
}

dependencies {
    implementation(project(":core-ui"))

    implementation(libs.androidx.activity.compose)
    implementation(libs.accompanist.flowlayout)
}