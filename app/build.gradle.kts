plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)
    alias(libs.plugins.hilt.android)
    kotlin("kapt")
    kotlin("plugin.serialization") version "2.0.21"
    alias(libs.plugins.navigation.safeargs)
    alias(libs.plugins.ksp)
    alias(libs.plugins.google.android.libraries.mapsplatform.secrets.gradle.plugin)
}

android {
    namespace = "com.szaniszlo.yettelhomeassignment"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.szaniszlo.yettelhomeassignment"
        minSdk = 24
        targetSdk = 35
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        release {
            isMinifyEnabled = true
            isShrinkResources = true
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }

        // uncommenting this setup is required to demonstrate that performance issues are present only in debug mode
        // because of Jetpack Compose

        /*debug {
            isMinifyEnabled = true
            isShrinkResources = true
            isDebuggable = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }*/
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    kotlinOptions {
        jvmTarget = "17"
    }

    buildFeatures {
        buildConfig = true
        compose = true
        viewBinding = false
    }
    secrets {
        // To add your Maps API key to this project:
        // 1. If the secrets.properties file does not exist, create it in the same folder as the local.properties file.
        // 2. Add this line, where YOUR_API_KEY is your API key:
        //        MAPS_API_KEY=YOUR_API_KEY
        propertiesFileName = "secrets.properties"

        // A properties file containing default secret values. This file can be
        // checked in version control.
        defaultPropertiesFileName = "local.defaults.properties"
    }

}

dependencies {

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    implementation(libs.androidx.activity)
    implementation(libs.androidx.core.splashscreen)
    implementation(libs.kotlinx.serialization.json)
    implementation(libs.kotlinx.collections.immutable)

    // Compose
    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.ui)
    implementation(libs.androidx.ui.graphics)
    implementation(libs.androidx.ui.tooling.preview)
    implementation(libs.androidx.material3)
    implementation(libs.androidx.material.icons.extended)

    // AndroidX ViewModel & Lifecycle Extensions
    implementation(libs.androidx.activity.ktx)
    implementation(libs.androidx.lifecycle.viewmodel.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)

    // Maps
    implementation(libs.play.services.maps)
    implementation(libs.maps.compose)
    implementation(libs.android.maps.utils)

    // DI
    implementation(libs.hilt.android)
    implementation(libs.play.services.maps)
    implementation(libs.androidx.constraintlayout)
    debugImplementation(libs.ui.tooling)
    kapt(libs.hilt.compiler)
    implementation(libs.androidx.hilt.navigation.compose)

    // Networking
    implementation(libs.retrofit)
    implementation(libs.logging.interceptor)
    implementation(libs.converter.gson)

    // Navigation
    implementation(libs.androidx.navigation.fragment.ktx)
    implementation(libs.androidx.navigation.ui)
    implementation(libs.androidx.navigation.compose)

    // Testing
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)

    testImplementation(libs.junit.jupiter.api)
    testRuntimeOnly(libs.junit.jupiter.engine)

    testImplementation(libs.kotlinx.coroutines.test)
    testImplementation(libs.androidx.runner)
    testImplementation(libs.androidx.rules)
    testImplementation(libs.truth)
    testImplementation(libs.androidx.truth)
    testImplementation(libs.turbine)
    testImplementation(libs.mockk)

    // Log
    implementation(libs.timber)
}

tasks.withType<Test> {
    useJUnitPlatform()
}