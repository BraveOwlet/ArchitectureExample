plugins {
    id("com.android.library")
    id("org.jetbrains.kotlin.android")
    id("dagger.hilt.android.plugin")
    id("org.jetbrains.kotlin.plugin.serialization")
    kotlin("kapt")
}

android {
    namespace = "ru.braveowlet.oauth"
    compileSdk = 33

    val yandexClientIdArchex = properties["yandexClientIdArchex"].toString()
    val googleClientIdArchex = properties["googleClientIdArchex"].toString()

    defaultConfig {
        minSdk = 26
        targetSdk = 33
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        consumerProguardFiles("consumer-rules.pro")
    }

    buildTypes {
        debug {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
            buildConfigField("String", "YANDEX_CLIENT_ID", "\"$yandexClientIdArchex\"")
            buildConfigField("String", "GOOGLE_CLIENT_ID", "\"$googleClientIdArchex\"")
        }
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
            buildConfigField("String", "YANDEX_CLIENT_ID", "\"$yandexClientIdArchex\"")
            buildConfigField("String", "GOOGLE_CLIENT_ID", "\"$googleClientIdArchex\"")
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    buildFeatures { compose = true }
    composeOptions { kotlinCompilerExtensionVersion = "1.4.3" }
    kotlinOptions {
        jvmTarget = "1.8"
    }
}

dependencies {

    implementation("androidx.core:core-ktx:1.9.0")
    implementation("androidx.appcompat:appcompat:1.6.1")

    //compose
    implementation("androidx.activity:activity-compose:1.6.1")
    implementation("androidx.compose.ui:ui:1.4.0-beta02")
    implementation("androidx.compose.ui:ui-tooling-preview:1.4.0-beta02")
    implementation("androidx.compose.material3:material3:1.1.0-alpha07")
    implementation("androidx.compose.animation:animation:1.4.0-beta02")
    implementation("androidx.compose.animation:animation-graphics:1.4.0-beta02")
    implementation("androidx.compose.runtime:runtime-livedata:1.4.0-beta01")
    implementation("androidx.compose.foundation:foundation:1.4.0-beta02")
    implementation("androidx.compose.material:material-icons-extended:1.4.0-beta02")
    implementation("androidx.navigation:navigation-compose:2.5.3")

    //lifecycle
    implementation("androidx.lifecycle:lifecycle-runtime-ktx:2.5.1")
    implementation("androidx.lifecycle:lifecycle-viewmodel-ktx:2.5.1")
    implementation("androidx.lifecycle:lifecycle-viewmodel-compose:2.5.1")
    implementation("androidx.lifecycle:lifecycle-livedata-ktx:2.5.1")
    implementation("androidx.lifecycle:lifecycle-common-java8:2.5.1")
    implementation("androidx.lifecycle:lifecycle-service:2.5.1")
    implementation("androidx.lifecycle:lifecycle-process:2.5.1")

    //yandex
    implementation("com.yandex.android:mobmetricalib:5.2.0")
    implementation("com.yandex.android:authsdk:2.3.0")

    //firebase
    implementation(platform("com.google.firebase:firebase-bom:31.1.1"))
    implementation("com.google.firebase:firebase-auth-ktx")
    implementation("com.google.android.gms:play-services-auth:20.4.1")

    //hilt
    implementation("com.google.dagger:hilt-android:2.45")
    implementation("androidx.hilt:hilt-navigation-compose:1.0.0")
    kapt("com.google.dagger:hilt-compiler:2.45")

    //serialization
    implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:1.4.1")
    implementation("com.google.code.gson:gson:2.10.1")

    //Retrofit //Okhttp3
    implementation("com.squareup.retrofit2:retrofit:2.9.0")
    implementation("com.squareup.retrofit2:converter-gson:2.9.0")
    implementation("com.squareup.okhttp3:logging-interceptor:5.0.0-alpha.2")
    implementation("com.squareup.okhttp3:okhttp-tls:4.9.1")
    implementation("com.squareup.okhttp3:okhttp:5.0.0-alpha.2")

    implementation(project(":mvi"))
}