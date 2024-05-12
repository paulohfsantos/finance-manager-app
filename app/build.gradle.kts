plugins {
  alias(libs.plugins.androidApplication)
  alias(libs.plugins.jetbrainsKotlinAndroid)
  alias(libs.plugins.googleServices)
}

android {
  namespace = "com.android.finance.manager"
  compileSdk = 34

  defaultConfig {
    applicationId = "com.android.finance.manager"
    minSdk = 34
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
      proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
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
  implementation(libs.credentials)
  implementation(libs.androidx.core.ktx)
  implementation(libs.androidx.lifecycle.runtime.ktx)
  implementation(libs.androidx.lifecycle.view.model)
  implementation(libs.androidx.activity.compose)
  implementation(platform(libs.androidx.compose.bom))
  implementation(platform(libs.firebaseBom))
  implementation(libs.androidx.ui)
  implementation(libs.androidx.ui.graphics)
  implementation(libs.androidx.ui.tooling.preview)
  implementation(libs.androidx.material3)
  implementation(libs.androidx.navigation.compose)
  implementation("androidx.credentials:credentials:1.2.0")
  // optional - needed for credentials support from play services, for devices running
  // Android 13 and below.
  implementation("androidx.credentials:credentials-play-services-auth:1.2.0")
  implementation("com.google.android.libraries.identity.googleid:googleid:1.1.0")
  implementation("androidx.compose.material:material-icons-extended:1.5.1")
  implementation("com.auth0.android:jwtdecode:2.0.2")
  implementation("com.google.code.gson:gson:2.10.1")
  // camerax dependencies
  // CameraX core library using the camera2 implementation
  val cameraxVersion = "1.4.0-alpha05"
  // The following line is optional, as the core library is included indirectly by camera-camera2
  implementation("androidx.camera:camera-core:$cameraxVersion")
  implementation("androidx.camera:camera-camera2:$cameraxVersion")
  implementation("androidx.camera:camera-lifecycle:$cameraxVersion")
  implementation("androidx.camera:camera-video:$cameraxVersion")

  implementation("androidx.camera:camera-view:$cameraxVersion")
  implementation("androidx.camera:camera-extensions:$cameraxVersion")
  // coil
  implementation(libs.coil.compose)
  // mlkit
  implementation(libs.mlkit.text.recognition)
  implementation("com.google.android.gms:play-services-mlkit-text-recognition-common:19.0.0")
  // firebase
  implementation(libs.firebase.auth)
  implementation(libs.firebase.auth.ktx)
  // google signin dependencies
  implementation(libs.play.services)
//  implementation(libs.accompanist)
//  implementation(libs.accompanist.permissions)
  implementation(libs.live.data)

  // google services + identity
//  implementation(libs.google.services.auth)
  implementation(libs.google.identity)
  implementation(libs.firestore)
  // Navigation
  implementation(libs.androidx.navigation.compose)
  // Coil
  implementation(libs.coil.compose)
  // Credential Manager
  implementation(libs.androidx.lifecycle.runtime.compose)

  // tests
  testImplementation(libs.junit)
  androidTestImplementation(libs.androidx.junit)
  androidTestImplementation(libs.androidx.espresso.core)
  androidTestImplementation(platform(libs.androidx.compose.bom))
  androidTestImplementation(libs.androidx.ui.test.junit4)
  debugImplementation(libs.androidx.ui.tooling)
  debugImplementation(libs.androidx.ui.test.manifest)
}