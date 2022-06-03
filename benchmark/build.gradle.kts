plugins {
    id("nowinandroid.android.test")
    id("nowinandroid.spotless")
}

android {
    namespace = "com.infinitybet.premierleague.benchmark"

    defaultConfig {
        minSdk = 23
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        // This benchmark buildType is used for benchmarking, and should function like your
        // release build (for example, with minification on). It's signed with a debug key
        // for easy local/CI testing.
        val benchmark by creating {
            isDebuggable = true
            signingConfig = signingConfigs.getByName("debug")
            matchingFallbacks.add("release")
        }
    }

    targetProjectPath = ":app"
    experimentalProperties["android.experimental.self-instrumenting"] = true
}

dependencies {
    implementation(libs.androidx.test.core)
    implementation(libs.androidx.test.espresso.core)
    implementation(libs.androidx.test.ext)
    implementation(libs.androidx.test.runner)
    implementation(libs.androidx.test.rules)
    implementation(libs.androidx.test.uiautomator)
    implementation(libs.androidx.benchmark.macro)
    implementation(libs.androidx.profileinstaller)
}

androidComponents {
    beforeVariants {
        it.enable = it.buildType == "benchmark"
    }
}