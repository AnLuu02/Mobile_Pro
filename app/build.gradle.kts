plugins {
    alias(libs.plugins.androidApplication)
    alias(libs.plugins.jetbrainsKotlinAndroid)
    id("com.google.gms.google-services")
    id("kotlin-kapt")
    id("org.jetbrains.kotlin.plugin.serialization") version "1.9.10"
}

android {
    namespace = "com.example.jetpackcomposedemo"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.example.jetpackcomposedemo"
        minSdk = 24
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }
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
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
    buildFeatures {
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.5.1"
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
}

dependencies {

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.ui)
    implementation(libs.androidx.ui.graphics)
    implementation(libs.androidx.ui.tooling.preview)
    implementation(libs.androidx.material3)
    implementation(libs.firebase.crashlytics.buildtools)
    implementation(libs.material)
    implementation(libs.firebase.auth.ktx)
    implementation(libs.androidx.material3.android)
    implementation(libs.firebase.database.ktx)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.ui.test.junit4)
    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)

    //navigation
    implementation("androidx.navigation:navigation-compose:2.7.7")

    //System UI Controller
    implementation("com.google.accompanist:accompanist-systemuicontroller:0.27.0")
    implementation("com.google.android.material:material:1.4.0")

    //Extended Icons
    implementation("androidx.compose.material:material-icons-extended:1.5.4")

    //material 3
    implementation("androidx.compose.material3:material3:1.2.1")

    // Video player
    implementation("com.google.android.exoplayer:exoplayer:2.16.1")

    //auto slide
    implementation("io.coil-kt:coil-compose:2.3.0")
    implementation("com.google.accompanist:accompanist-pager:0.35.0-alpha")
    implementation("androidx.compose.ui:ui:1.4.0-beta01")

    //ViewModel
    implementation("androidx.lifecycle:lifecycle-viewmodel-compose:2.6.1")
    implementation ("androidx.lifecycle:lifecycle-viewmodel-ktx:2.3.1")

    //Firebase
    implementation(platform("com.google.firebase:firebase-bom:32.8.0"))
    implementation("com.google.firebase:firebase-auth")
    implementation("com.google.firebase:firebase-analytics")
    implementation("com.google.android.gms:play-services-auth:19.0.0")


    // Livedata
    implementation ("androidx.lifecycle:lifecycle-livedata-ktx:2.3.1")
    implementation ("androidx.compose.runtime:runtime-livedata:1.6.6")

    //room database
    val  room_version = "2.6.1" // Check for the latest version
    implementation ("androidx.room:room-runtime:$room_version")
    implementation ("androidx.room:room-ktx:$room_version")
    kapt("androidx.room:room-compiler:$room_version")

    //permission
    implementation("com.google.accompanist:accompanist-permissions:0.34.0")

    //work manager
    implementation ("androidx.work:work-runtime-ktx:2.7.0")
    implementation ("androidx.core:core-ktx:1.6.0")
    implementation ("androidx.appcompat:appcompat:1.3.0")

    // Retrofit and GSON
    implementation ("com.squareup.retrofit2:retrofit:2.9.0")
    implementation ("com.squareup.retrofit2:converter-gson:2.9.0")
    // Coroutine support for Retrofit
    implementation ("com.squareup.retrofit2:adapter-rxjava3:2.9.0")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-rx3:1.5.0")
    // Logging
    implementation ("com.squareup.okhttp3:okhttp:4.11.0")
    implementation ("com.squareup.okhttp3:logging-interceptor:4.10.0")

    // Retrofit - JSON ViDAT
    implementation("com.squareup.retrofit2:retrofit:2.9.0")
    implementation("com.jakewharton.retrofit:retrofit2-kotlinx-serialization-converter:1.0.0")
    implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:1.5.1")
    implementation("org.jetbrains.kotlinx:kotlinx-datetime:0.4.0")
    //------------------------------------payment--------------------------------
    //zalopay
    implementation(fileTree(mapOf("dir" to "libs", "include" to listOf("*.aar"))))
    // Scan QR
//    implementation("androidx.camera:camera-camera2:1.0.2")
//    implementation("androidx.camera:camera-lifecycle:1.0.2")
//    implementation("androidx.camera:camera-view:1.0.0-alpha31")
//
//    implementation("com.google.zxing:core:3.3.3")
    // CameraX dependencies
    implementation("androidx.camera:camera-camera2:1.1.0-beta01")
    implementation("androidx.camera:camera-lifecycle:1.1.0-beta01")

    // ZXing (QR code scanning library) dependency
    implementation("com.google.zxing:core:3.4.1")
    implementation("com.journeyapps:zxing-android-embedded:4.3.0")

}

