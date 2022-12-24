import com.codingfeline.buildkonfig.compiler.FieldSpec.Type.STRING
import java.io.File
import java.io.FileInputStream
import java.util.*

plugins {
    kotlin("multiplatform")
    kotlin("native.cocoapods")
    kotlin("plugin.serialization")
    id("com.android.library")
    id("com.squareup.sqldelight")
    id("com.codingfeline.buildkonfig")
}

val coroutinesVersion = "1.5.0-native-mt"
val serializationVersion = "1.2.2"
val ktorVersion = "1.6.1"
val datetime = "0.3.2"
val uuidVersion = "0.4.0"
val koin = "3.2.0"

version = "1.0"

//val secrets: String = gradleLocalProperties(rootDir).getProperty("secrets")
val secrets = Properties().apply {
    load(FileInputStream(File(rootProject.rootDir, "secrets.properties")))
}

println("Property:" + secrets.getProperty("OLD_API_BASE_URL"))

kotlin {
    android()
    iosX64()
    iosArm64()
    //iosSimulatorArm64() sure all ios dependencies support this target

    val sqlDelightVersion: String by project

    cocoapods {
        summary = "Some description for the Shared Module"
        homepage = "Link to the Shared Module homepage"
        ios.deploymentTarget = "14.1"
        podfile = project.file("../iosApp/Podfile")
        framework {
            baseName = "shared"
            isStatic = false
        }
    }
    
    sourceSets {
        val commonMain by getting {
            dependencies {
                implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:$coroutinesVersion")
                implementation("org.jetbrains.kotlinx:kotlinx-serialization-core:$serializationVersion")
                implementation("io.ktor:ktor-client-core:$ktorVersion")
                implementation("io.ktor:ktor-client-serialization:$ktorVersion")
                implementation("com.squareup.sqldelight:runtime:$sqlDelightVersion")
                implementation("org.jetbrains.kotlinx:kotlinx-datetime:$datetime")
                implementation("com.benasher44:uuid:$uuidVersion")
                implementation("io.insert-koin:koin-core:$koin")
            }
        }
        val commonTest by getting {
            dependencies {
                implementation(kotlin("test-common"))
                implementation(kotlin("test-annotations-common"))
            }
        }
        val androidMain by getting {
            dependencies {
                implementation("io.ktor:ktor-client-android:$ktorVersion")
                implementation("com.squareup.sqldelight:android-driver:$sqlDelightVersion")
            }
        }
        val androidTest by getting {
            dependencies {
                implementation(kotlin("test-junit"))
                implementation("junit:junit:4.13.2")
            }
        }

        val iosX64Main by getting
        val iosArm64Main by getting
        val iosMain by creating {
            dependsOn(commonMain)
            iosX64Main.dependsOn(this)
            iosArm64Main.dependsOn(this)
            dependencies {
                implementation("io.ktor:ktor-client-ios:$ktorVersion")
                implementation("com.squareup.sqldelight:native-driver:$sqlDelightVersion")
            }
        }
        val iosX64Test by getting
        val iosArm64Test by getting
        //val iosSimulatorArm64Test by getting
        val iosTest by creating {
            dependsOn(commonTest)
            iosX64Test.dependsOn(this)
            iosArm64Test.dependsOn(this)
            //iosSimulatorArm64Test.dependsOn(this)
        }
    }
}

android {
    compileSdk = 31
    sourceSets["main"].manifest.srcFile("src/androidMain/AndroidManifest.xml")
    defaultConfig {
        minSdk = 27
        targetSdk = 31
    }
    namespace = "com.douglasqueiroz.mywallet"
}

sqldelight {
    database("AppDatabase") {
        packageName = "com.douglasqueiroz.mywallet"
    }
}

buildkonfig {
    packageName = "com.douglasqueiroz.mywallet"

    defaultConfigs {
        buildConfigField(STRING, "OLD_API_BASE_URL", secrets.getProperty("OLD_API_BASE_URL"))
        buildConfigField(STRING, "QUOTE_KEY", secrets.getProperty("QUOTE_KEY"))
    }
}