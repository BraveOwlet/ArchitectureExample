plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    id("dagger.hilt.android.plugin")
    id("org.jetbrains.kotlin.plugin.serialization")
    id("com.google.gms.google-services")
    kotlin("kapt")
}

android {
    namespace = "ru.braveowlet.archex"
    compileSdk = 33
    val yandexClientIdArchex = properties["yandexClientIdArchex"].toString()

    defaultConfig {
        applicationId = "ru.braveowlet.archex"
        manifestPlaceholders["YANDEX_CLIENT_ID"] = yandexClientIdArchex
        minSdk = 26
        targetSdk = 33
        versionCode = 1
        versionName = "1.0"
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables { useSupportLibrary = true }
    }
    buildTypes {
        debug {
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
            versionNameSuffix  = "-debug"
            isMinifyEnabled = false
        }
        release {
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
            versionNameSuffix  = ""
            isMinifyEnabled = true
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions { jvmTarget = "1.8" }
    buildFeatures { compose = true }
    composeOptions { kotlinCompilerExtensionVersion = "1.4.3" }
    packagingOptions { resources { excludes += "/META-INF/{AL2.0,LGPL2.1}" } }
}

dependencies {
    //android dependencies
    implementation("androidx.core:core-ktx:1.9.0")
    implementation("androidx.appcompat:appcompat:1.6.1")

    //splash screen
    implementation("androidx.core:core-splashscreen:1.0.0")

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

    //coroutines
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.6.4")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-android:1.6.4")

    //hilt
    implementation("com.google.dagger:hilt-android:2.45")
    implementation("androidx.hilt:hilt-navigation-compose:1.0.0")
    kapt("com.google.dagger:hilt-compiler:2.45")

    //serialization
    implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:1.4.1")
    implementation("com.google.code.gson:gson:2.10.1")

    //room
    annotationProcessor("androidx.room:room-compiler:2.5.0")
    kapt("androidx.room:room-compiler:2.5.0")
    implementation("androidx.room:room-runtime:2.5.0")
    implementation("androidx.room:room-ktx:2.5.0")
    testImplementation("androidx.room:room-testing:2.5.0")

    //yandex
    implementation("com.yandex.android:mobmetricalib:5.2.0")
    implementation("com.yandex.android:authsdk:2.3.0")

    //firebase
    implementation(platform("com.google.firebase:firebase-bom:31.1.1"))
    implementation("com.google.firebase:firebase-auth-ktx")
    implementation("com.google.android.gms:play-services-auth:20.4.1")

    //google.accompanist dependencies
    implementation("com.google.accompanist:accompanist-systemuicontroller:0.28.0")
    implementation("com.google.accompanist:accompanist-pager:0.28.0")
    implementation("com.google.accompanist:accompanist-pager-indicators:0.28.0")
    implementation("com.google.accompanist:accompanist-permissions:0.28.0")
    implementation("com.google.accompanist:accompanist-navigation-animation:0.28.0")

    //debug
    debugImplementation("androidx.compose.ui:ui-tooling:1.4.0-beta02")
    debugImplementation("androidx.compose.ui:ui-test-manifest:1.4.0-beta02")

    //Retrofit //Okhttp3
    implementation("com.squareup.retrofit2:retrofit:2.9.0")
    implementation("com.squareup.retrofit2:converter-gson:2.9.0")
    implementation("com.squareup.okhttp3:logging-interceptor:5.0.0-alpha.2")
    implementation("com.squareup.okhttp3:okhttp-tls:4.9.1")
    implementation("com.squareup.okhttp3:okhttp:5.0.0-alpha.2")

    //modules
    implementation(project(":oauth"))

}