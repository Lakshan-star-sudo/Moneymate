plugins {
    alias(libs.plugins.android.application)

    // Connects google-services.json with the Android app
    id("com.google.gms.google-services")
}

android {
    namespace = "com.nibm.moneymate"

    compileSdk {
        version = release(36)
    }

    defaultConfig {
        applicationId = "com.nibm.moneymate"
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

    // Android libraries
    implementation(libs.appcompat)
    implementation(libs.material)
    implementation(libs.activity)
    implementation(libs.constraintlayout)

    // Firebase BoM - manages compatible Firebase library versions
    implementation(platform("com.google.firebase:firebase-bom:34.15.0"))

    // Firebase Authentication
    implementation("com.google.firebase:firebase-auth")

    // Cloud Firestore Database
    implementation("com.google.firebase:firebase-firestore")

    // Firebase Storage for warranty card and receipt images
    implementation("com.google.firebase:firebase-storage")

    // Testing libraries
    testImplementation(libs.junit)
    androidTestImplementation(libs.ext.junit)
    androidTestImplementation(libs.espresso.core)
}