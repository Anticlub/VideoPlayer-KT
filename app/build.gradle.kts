plugins {
    alias(libs.plugins.android.application)
}

android {
    namespace = "com.example.videoplayer_kt"
    compileSdk = 36

    defaultConfig {
        applicationId = "com.example.videoplayer_kt"
        minSdk = 24
        targetSdk = 36
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
}

dependencies {
    // Coroutines
    implementation(libs.coroutines.android)
    // ExoPlayer
    implementation(libs.exoplayer.core)
    implementation(libs.exoplayer.hls)
    implementation(libs.exoplayer.ui)
    // Retrofit
    implementation(libs.retrofit)
    implementation(libs.converter.scalar)
    implementation(libs.okhttp.interceptor)
    // Navigation
    implementation(libs.navigation.fragment)
    implementation(libs.navigation.ui)
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    implementation(libs.androidx.activity)
    implementation(libs.androidx.constraintlayout)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
}