plugins {
    id("com.android.application")
    kotlin("android")
}

android {
    compileSdk = 33
    defaultConfig {
        applicationId = "com.douglasqueiroz.mywallet.android"
        minSdk = 27
        targetSdk = 33
        versionCode = 1
        versionName = "1.0"
    }
    buildTypes {
        getByName("release") {
            isMinifyEnabled = false
        }
    }
    buildFeatures {
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.3.2"
    }
    namespace = "com.douglasqueiroz.mywallet.android"
}

dependencies {
    implementation(project(":shared"))

    val koinAndroidVersion= "3.3.2"
    val workManager = "2.7.1"
    val navVersion = "2.5.3"
    val koinJetpackCompose = "3.4.1"

    implementation("androidx.compose.material:material:1.3.1")
    implementation("androidx.compose.ui:ui-tooling-preview:1.4.0-alpha03")
    implementation("androidx.activity:activity-compose:1.6.1")
    implementation("androidx.lifecycle:lifecycle-viewmodel-compose:2.5.1")
    implementation("androidx.lifecycle:lifecycle-viewmodel-ktx:2.5.1")
    implementation("androidx.navigation:navigation-compose:$navVersion")
    implementation("io.insert-koin:koin-android:$koinAndroidVersion")
    implementation("io.insert-koin:koin-android-compat:$koinAndroidVersion")
    implementation("io.insert-koin:koin-androidx-compose:$koinJetpackCompose")
    debugImplementation("androidx.compose.ui:ui-tooling:1.4.0-alpha03")

    // Work Manager
    implementation("androidx.work:work-runtime-ktx:$workManager")
    implementation("androidx.work:work-gcm:$workManager")

    implementation("com.google.android.material:material:1.7.0")
    implementation("androidx.appcompat:appcompat:1.5.1")
    implementation("androidx.constraintlayout:constraintlayout:2.1.4")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-android:1.6.4-native-mt")
}