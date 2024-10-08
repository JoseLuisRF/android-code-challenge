import Deps.hiltModule
import Deps.junit5
import Deps.mockk
import java.util.Properties

plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    kotlin("kapt")
    id("dagger.hilt.android.plugin")
}

val tmdbToken = getLocalProperty(key = "tmdb.authorization_token") ?: System.getenv("TMDB_TOKEN")

fun Project.getLocalProperty(key: String, file: String = "local.properties"): Any? {
    val localProperties = Properties()
    try {
        localProperties.load(project.rootProject.file("local.properties").inputStream())
        return localProperties.getProperty("tmdb.authorization_token")
    } catch (ex: Exception) {
        return null
    }
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

        testInstrumentationRunner = "com.jlrf.mobile.employeepedia.CustomTestRunner"

        buildConfigField("String", "TMDB_TOKEN", "\"$tmdbToken\"")
    }

    buildTypes {
        getByName("release") {
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
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
    testOptions {
        unitTests.all {
            it.useJUnitPlatform() // Ensure unit tests use JUnit Platform

            // (Optional) Set JVM arguments for unit tests
            it.jvmArgs("-XX:+HeapDumpOnOutOfMemoryError", "-Xmx2048m") // Example JVM arguments
        }
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
            excludes += "META-INF/LICENSE.md"
            excludes += "META-INF/LICENSE-notice.md"
        }
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

    implementation(AndroidX.paging)
    implementation(AndroidX.pagingRuntime)

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
    implementation(Deps.coil)
    implementation(Deps.coilGif)

    // DI
    hiltModule()
    // For instrumented tests.
    androidTestImplementation(Deps.hiltAndroidTesting)
    // ...with Kotlin.
    kaptAndroidTest(Deps.daggerHiltAndroidProcessor)

    // Other
    implementation(Deps.retrofit)
    implementation(Deps.retrofitGsonConverter)
    implementation(Deps.okhttp3LoggingInterceptor)
    implementation(Deps.coroutinesCore)
    implementation(Deps.arrow)
    implementation(Deps.gson)

    // Testing
    junit5()

    androidTestImplementation(Deps.espressoCore)
    androidTestImplementation(AndroidX.testCore)
    androidTestImplementation(AndroidX.testCoreKtx)
    androidTestImplementation(AndroidX.testRunner)
    androidTestImplementation(AndroidX.testRules)
    androidTestImplementation(AndroidX.coreKtx)

    androidTestImplementation(Compose.uiTests)
    androidTestImplementation(AndroidX.testExt)
    androidTestImplementation(platform(Compose.composeBom))


    debugImplementation(Compose.uiTooling)
    debugImplementation(Compose.uiTestsManifest)

    // unit test
    testImplementation(Deps.coroutinesTest)
    mockk()
}