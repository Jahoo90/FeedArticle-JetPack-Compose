plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)


    id("kotlin-parcelize")
    // Hilt
    id("com.google.dagger.hilt.android")
    kotlin("kapt")
}

android {
    namespace = "com.devid.feedarticlescompose"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.devid.feedarticlescompose"
        minSdk = 25
        targetSdk = 34
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
    kotlinOptions {
        jvmTarget = "11"
        freeCompilerArgs = listOf("-Xopt-in=kotlin.RequiresOptIn")
    }
    buildFeatures {
        compose = true
    }
}

dependencies {

    val composeVersion = "1.7.8"
    val hiltVersion = "2.55"
    val retrofitVersion = "2.9.0"
    val moshiVersion = "1.15.0"
    val kotlin_version = "1.8.0"

    implementation ("org.jetbrains.kotlin:kotlin-stdlib:$kotlin_version")
//
    implementation ("androidx.compose.ui:ui:$composeVersion")
    implementation ("androidx.lifecycle:lifecycle-viewmodel-compose:2.4.0")


    implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:1.5.1")

// Compose
    implementation("androidx.compose.runtime:runtime:$composeVersion")
    implementation("androidx.compose.runtime:runtime-livedata:$composeVersion")
    implementation("androidx.compose.material:material-icons-extended:$composeVersion")

// ViewModel & Lifecycle
    implementation("androidx.lifecycle:lifecycle-viewmodel-compose:2.7.0")

// Navigation
    implementation("androidx.navigation:navigation-compose:2.8.8")
    implementation("androidx.hilt:hilt-navigation-compose:1.2.0")

// Hilt (Dependency Injection)
    implementation("com.google.dagger:hilt-android:$hiltVersion")
    kapt("com.google.dagger:hilt-android-compiler:$hiltVersion")

// Network - OkHttp
    implementation("com.squareup.okhttp3:logging-interceptor:4.12.0")

// Network - Retrofit & Moshi
    implementation("com.squareup.retrofit2:retrofit:$retrofitVersion")
    implementation("com.squareup.retrofit2:converter-moshi:$retrofitVersion")
    implementation("com.squareup.moshi:moshi:$moshiVersion")
    implementation("com.squareup.moshi:moshi-kotlin:$moshiVersion")
    kapt("com.squareup.moshi:moshi-kotlin-codegen:$moshiVersion")

// Image Loading - Coil
    implementation("io.coil-kt:coil-compose:2.2.2")


    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.ui)
    implementation(libs.androidx.ui.graphics)
    implementation(libs.androidx.ui.tooling.preview)
    implementation(libs.androidx.material3)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.ui.test.junit4)
    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)
}