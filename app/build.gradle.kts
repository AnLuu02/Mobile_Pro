plugins {
    alias(libs.plugins.androidApplication)
    alias(libs.plugins.jetbrainsKotlinAndroid)
    id("com.google.gms.google-services")
    id("kotlin-kapt")
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
    implementation ("androidx.compose.material3:material3:1.1.0-alpha08")



    // Android Maps Compose composables for the Maps SDK for Android
    implementation ("com.google.maps.android:maps-compose:4.3.3")

//auto slide
    implementation("io.coil-kt:coil-compose:2.3.0")
    implementation("com.google.accompanist:accompanist-pager:0.35.0-alpha")
    implementation("androidx.compose.ui:ui:1.4.0-beta01")

    //ViewModel
    implementation("androidx.lifecycle:lifecycle-viewmodel-compose:2.6.1")

    //Firebase
    implementation(platform("com.google.firebase:firebase-bom:32.8.0"))
    implementation("com.google.firebase:firebase-auth")
    implementation("com.google.firebase:firebase-analytics")


    // ViewModel
    implementation ("androidx.lifecycle:lifecycle-viewmodel-ktx:2.3.1")
    // Livedata
    implementation ("androidx.lifecycle:lifecycle-livedata-ktx:2.3.1")
    implementation ("androidx.compose.runtime:runtime-livedata:1.6.6")

    //retrofit
    // Retrofit and GSON
    implementation ("com.squareup.retrofit2:retrofit:2.9.0")
    implementation ("com.squareup.retrofit2:converter-gson:2.9.0")

    // Coroutine support for Retrofit
    implementation ("com.squareup.retrofit2:adapter-rxjava3:2.9.0")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-rx3:1.5.0")

    // Logging
    implementation ("com.squareup.okhttp3:okhttp:4.11.0")
    implementation ("com.squareup.okhttp3:logging-interceptor:4.10.0")

    //room database
    val  room_version = "2.6.1" // Check for the latest version

    implementation ("androidx.room:room-runtime:$room_version")
    // Optional - Kotlin Extensions and Coroutines support for Room
    implementation ("androidx.room:room-ktx:$room_version")
    kapt("androidx.room:room-compiler:$room_version")


    //permission
    implementation("com.google.accompanist:accompanist-permissions:0.34.0")

}