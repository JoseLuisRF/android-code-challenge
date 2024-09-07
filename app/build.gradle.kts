import Deps.hiltModule
import Deps.junit5

plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    kotlin("kapt")
    id("dagger.hilt.android.plugin")
}

android {
    namespace = "com.jlrf.mobile.employeepedia"
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
        sourceCompatibility = JavaVersion.VERSION_18
        targetCompatibility = JavaVersion.VERSION_18
    }
    kotlinOptions {
        jvmTarget = "18"
    }

    buildFeatures {
        compose = true
        buildConfig = true
    }

    composeOptions {
        kotlinCompilerExtensionVersion = "1.4.3"
    }
}

// Allow references to generated code
kapt {
    correctErrorTypes = true
}

dependencies {
    implementation(AndroidX.coreKtx)
    implementation(AndroidX.lifecycleRuntime)
    implementation(AndroidX.ktxViewModel)
    implementation(AndroidX.worker)

    // Android Compose
    implementation(platform(Compose.composeBom))
    implementation(Compose.activityCompose)
    implementation(Compose.composeUI)
    implementation(Compose.graphicsUI)
    implementation(Compose.composeNavigation)
    implementation(Compose.composeConstraintLayout)
    implementation(Compose.toolingPreview)
    implementation(Compose.material3)
    implementation(Compose.material3WindowSize)

//    implementation("com.google.accompanist:accompanist-drawablepainter:0.16.0")

    // DI
    hiltModule()

    // Other
    implementation(Deps.retrofit)
    implementation(Deps.retrofitGsonConverter)
    implementation(Deps.okhttp3LoggingInterceptor)
    implementation(Deps.coroutinesCore)
    implementation("io.arrow-kt:arrow-core:1.0.1")
    implementation(Deps.gson)

    // Testing
    junit5()

    androidTestImplementation(AndroidX.testExt)
    androidTestImplementation(Deps.espressoCore)
    androidTestImplementation(platform(Compose.composeBom))
    androidTestImplementation(Compose.uiTests)
    debugImplementation(Compose.uiTooling)
    debugImplementation(Compose.uiTestsManifest)
}