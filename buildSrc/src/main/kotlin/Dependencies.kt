import org.gradle.kotlin.dsl.DependencyHandlerScope

@Suppress("ktlint:standard:property-naming")
object Versions {
    const val targetSdkVersion = 34
    const val compileSdkVersion = 34
    const val minSdkVersion = 28
    const val versionCode = 1
}

@Suppress("ktlint:standard:property-naming")
object AndroidX {
    object Versions {
        const val activityKtx = "1.7.2"
        const val coreKtx = "1.9.0"
        const val archLifeCycle = "2.6.2"
        const val room = "2.5.1"
        const val datastore = "1.0.0"
        const val worker = "2.7.1"
        const val paging = "3.3.1"
    }

    const val coreKtx = "androidx.core:core-ktx:${Versions.coreKtx}"
    const val datastore = "androidx.datastore:datastore-preferences:${Versions.datastore}"
    const val protoDatastore = "androidx.datastore:datastore:${Versions.datastore}"
    const val lifecycleRuntime =
        "androidx.lifecycle:lifecycle-runtime-ktx:${Versions.archLifeCycle}"
    const val testExt = "androidx.test.ext:junit:1.1.5"

    const val roomRuntime = "androidx.room:room-runtime:${Versions.room}"
    const val roomKtx = "androidx.room:room-ktx:${Versions.room}"
    const val roomCompiler = "androidx.room:room-compiler:${Versions.room}"
    const val roomTest = "androidx.room:room-testing:${Versions.room}"

    const val ktxViewModel = "androidx.lifecycle:lifecycle-viewmodel-ktx:${Versions.archLifeCycle}"
    const val worker = "androidx.work:work-runtime-ktx:${Versions.worker}"
    const val paging = "androidx.paging:paging-compose:${Versions.paging}"
    const val pagingRuntime = "androidx.paging:paging-runtime:${Versions.paging}"
    const val pagingCommon = "androidx.paging:paging-common:${Versions.paging}"

    fun DependencyHandlerScope.roomModule() {
        add("implementation", roomRuntime)
        add("implementation", roomKtx)
        add("kapt", roomCompiler)
        add("testImplementation", roomTest)
    }
}

@Suppress("ktlint:standard:property-naming")
object Compose {
    object Versions {
        const val composeBom = "2024.06.00"
        const val constraintLayout = "1.0.1"
        const val compose = "2.7.6"
        const val material3 = "1.2.1"
        const val composeTools = "1.6.8"
    }

    const val composeNavigation = "androidx.navigation:navigation-compose:${Versions.compose}"
    const val composeBom = "androidx.compose:compose-bom:${Versions.composeBom}"
    const val composeUI = "androidx.compose.ui:ui:${Versions.composeTools}"
    const val composeConstraintLayout =
        "androidx.constraintlayout:constraintlayout-compose:${Versions.constraintLayout}"
    const val graphicsUI = "androidx.compose.ui:ui-graphics:${Versions.composeTools}"
    const val activityCompose =
        "androidx.activity:activity-compose:${AndroidX.Versions.activityKtx}"

    // Tooling support (Previews, etc)
    const val toolingPreview = "androidx.compose.ui:ui-tooling-preview:${Versions.composeTools}"
    const val composeUiTooling = "androidx.compose.ui:ui-tooling:${Versions.composeTools}"

    // Material Design
    const val material3 = "androidx.compose.material3:material3:${Versions.material3}"
    const val material3WindowSize =
        "androidx.compose.material3:material3-window-size-class:${Versions.material3}"

    // UI Tests
    const val uiTests = "androidx.compose.ui:ui-test-junit4:${Versions.composeTools}"
    const val uiTestsManifest = "androidx.compose.ui:ui-test-manifest:${Versions.composeTools}"
    const val uiTooling = "androidx.compose.ui:ui-tooling:${Versions.composeTools}"

    const val kotlinSerialization = "org.jetbrains.kotlinx:kotlinx-serialization-json:1.6.0"
}

@Suppress("ktlint:standard:property-naming")
object Deps {
    object Versions {
        const val hilt = "2.44"
        const val mockk = "1.13.9"
        const val junit5 = "5.8.1"
        const val espresso = "3.5.1"
        const val retrofit = "2.9.0"
        const val coroutines = "1.4.1"
        const val okhttp3 = "4.8.0"
        const val hiltNavigationCompose = "1.1.0"
        const val hiltWorker = "1.0.0"
        const val protobufVersion = "3.19.3"
        const val gson = "2.10.1"
    }

    const val retrofit = "com.squareup.retrofit2:retrofit:${Versions.retrofit}"
    const val retrofitGsonConverter = "com.squareup.retrofit2:converter-gson:${Versions.retrofit}"
    //OKHttp
    const val okhttp3LoggingInterceptor =
        "com.squareup.okhttp3:logging-interceptor:${Versions.okhttp3}"
    const val gson = "com.google.code.gson:gson:${Versions.gson}"
    const val daggerHilt = "com.google.dagger:hilt-android:${Versions.hilt}"
    const val daggerHiltAndroidProcessor =
        "com.google.dagger:hilt-android-compiler:${Versions.hilt}"
    const val daggerHiltNavigation =
        "androidx.hilt:hilt-navigation-compose:${Versions.hiltNavigationCompose}"
    const val daggerHiltWorker = "androidx.hilt:hilt-work:${Versions.hiltWorker}"
    const val daggerHiltCompiler = "androidx.hilt:hilt-compiler:${Versions.hiltWorker}"

    /* JUnit */
    const val jupiterApi = "org.junit.jupiter:junit-jupiter-api:${Versions.junit5}"
    const val junit5 = "org.junit.jupiter:junit-jupiter:${Versions.junit5}"
    const val jupiterEngine = "org.junit.jupiter:junit-jupiter-engine:${Versions.junit5}"
    const val jupiterParameterized = "org.junit.jupiter:junit-jupiter-params:${Versions.junit5}"
    const val junitVintageEngine = "org.junit.vintage:junit-vintage-engine:${Versions.junit5}"

    /* Mockk */
    const val mockk = "io.mockk:mockk:${Versions.mockk}"
    const val mockkAndroid = "io.mockk:mockk-android:${Versions.mockk}"

    const val espressoCore = "androidx.test.espresso:espresso-core:${Versions.espresso}"

    const val coroutinesCore =
        "org.jetbrains.kotlinx:kotlinx-coroutines-core:${Versions.coroutines}"
    const val coroutinesTest =
        "org.jetbrains.kotlinx:kotlinx-coroutines-test:${Versions.coroutines}"

    fun DependencyHandlerScope.hiltModule() {
        add("implementation", daggerHilt)
        add("kapt", daggerHiltAndroidProcessor)
        add("implementation", daggerHiltNavigation)
        add("implementation", daggerHiltWorker)
        add("kapt", daggerHiltCompiler)
    }

    fun DependencyHandlerScope.junit5Only() {
        add("testImplementation", jupiterApi)
        add("testRuntimeOnly", jupiterEngine)
        add("testImplementation", jupiterParameterized)
        add("testImplementation", junit5)
    }

    fun DependencyHandlerScope.junit5() {
        junit5Only()
        add("testImplementation", junitVintageEngine)
    }

    fun DependencyHandlerScope.mockk() {
        add("testImplementation", mockk)
        add("androidTestImplementation", mockkAndroid)
    }
}