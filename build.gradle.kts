// Top-level build file where you can add configuration options common to all sub-projects/modules.
buildscript {
    val agp_version by extra("8.1.2")
    repositories {
        mavenCentral()
        google()
        mavenLocal()
    }

    dependencies {
        classpath("com.android.tools.build:gradle:$agp_version")
        classpath("androidx.navigation:navigation-safe-args-gradle-plugin:2.5.2")
        classpath("org.jetbrains.kotlin:kotlin-gradle-plugin:1.8.10")
        classpath("org.jetbrains.kotlin:kotlin-android-extensions:1.8.10")
        classpath("org.jetbrains.kotlin:kotlin-serialization:1.8.10")
        classpath("com.google.dagger:hilt-android-gradle-plugin:${GoogleLib.Versions.hiltVersion}")
    }
}