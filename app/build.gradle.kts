plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    kotlin("kapt")
    id("dagger.hilt.android.plugin")
    id("androidx.navigation.safeargs.kotlin")
}

android {
    compileSdk = Versions.compileSdkVersion

    defaultConfig {
        applicationId = "com.jlrf.mobile.employeepedia"
        minSdk = Versions.minSdkVersion
        targetSdk = Versions.targetSdkVersion
        versionCode = Versions.versionCode
        versionName = "1.0.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        getByName("release") {
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
            proguardFile("proguard-rules.pro")
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
        viewBinding = false
        compose = true
    }

    composeOptions {
        kotlinCompilerExtensionVersion = "1.3.1"
    }
}

// Allow references to generated code
kapt {
    correctErrorTypes = true
}

dependencies {
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-android:1.6.4")
    // AndroidX - UI
    implementation("androidx.appcompat:appcompat:1.4.1")

    // AndroidX - Lifecycle
    implementation("androidx.lifecycle:lifecycle-viewmodel-ktx:${AndroidX.Versions.lifecycleVersion}")
    implementation("androidx.lifecycle:lifecycle-livedata-ktx:${AndroidX.Versions.lifecycleVersion}")

    // AndroidX - Compose
    implementation("androidx.compose.runtime:runtime:${AndroidX.Versions.composeVersion}")
    implementation("androidx.compose.ui:ui:${AndroidX.Versions.composeVersion}")
    implementation("androidx.compose.foundation:foundation:${AndroidX.Versions.composeVersion}")
    implementation("androidx.compose.foundation:foundation-layout:${AndroidX.Versions.composeVersion}")
    implementation("androidx.compose.material:material:${AndroidX.Versions.composeVersion}")
    implementation("androidx.compose.runtime:runtime-livedata:${AndroidX.Versions.composeVersion}")
    implementation("androidx.compose.ui:ui-tooling:${AndroidX.Versions.composeVersion}")
    implementation("androidx.constraintlayout:constraintlayout-compose:1.0.1")
    implementation("com.google.accompanist:accompanist-drawablepainter:0.16.0")
    implementation("com.squareup.okhttp3:logging-interceptor:4.10.0")


    // AndroidX - Navigation
    implementation("androidx.navigation:navigation-fragment-ktx:2.5.2")
    implementation("androidx.navigation:navigation-ui-ktx:2.5.2")

    // DI
    implementation("com.google.dagger:hilt-android:${GoogleLib.Versions.hiltVersion}")
    kapt("com.google.dagger:hilt-android-compiler:${GoogleLib.Versions.hiltVersion}")

    // Other
    implementation("com.squareup.retrofit2:retrofit:2.9.0")
    implementation("com.squareup.retrofit2:converter-gson:2.9.0")
    implementation("io.arrow-kt:arrow-core:1.0.1")
    implementation("com.google.code.gson:gson:2.9.1")

    // Testing
    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.1")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.1.0")
    testImplementation("androidx.arch.core:core-testing:${AndroidX.Versions.archVersion}")
}