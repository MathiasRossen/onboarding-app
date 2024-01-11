plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
}

android {
    namespace = "dk.mathiasrossen.onboardingapp"
    compileSdk = 34

    defaultConfig {
        applicationId = "dk.mathiasrossen.onboardingapp"
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
    val composeBom = platform("androidx.compose:compose-bom:${Versions.COMPOSE_BOM_VERSION}")
    implementation(composeBom)
    androidTestImplementation(composeBom)

    // Android libraries
    implementation("androidx.core:core-ktx:${Versions.ANDROIDX_CORE_VERSION}")
    implementation("androidx.core:core-splashscreen:${Versions.ANDROIDX_CORE_SPLASHSCREEN_VERSION}")
    implementation("androidx.lifecycle:lifecycle-runtime-ktx:${Versions.ANDROIDX_LIFECYCLE_VERSION}")
    implementation("androidx.activity:activity-compose:${Versions.ANDROIDX_ACTIVITY_COMPOSE_VERSION}")
    implementation("androidx.navigation:navigation-compose:${Versions.ANDROIDX_NAVIGATION_COMPOSE_VERSION}")

    // Jetpack Compose
    implementation("androidx.compose.ui:ui")
    implementation("androidx.compose.ui:ui-graphics")
    implementation("androidx.compose.ui:ui-tooling-preview")
    implementation("androidx.compose.material3:material3")
    implementation("androidx.compose.material:material")

    // Retrofit
    implementation("com.squareup.retrofit2:retrofit:${Versions.RETROFIT_VERSION}")

    // Test implementations
    testImplementation("junit:junit:${Versions.JUNIT_VERSION}")
    androidTestImplementation("androidx.test.ext:junit:${Versions.ANDROIDX_JUNIT_VERSION}")
    androidTestImplementation("androidx.test.espresso:espresso-core:${Versions.ANDROIDX_ESPRESSO_VERSION}")

    // Test implementations for Jetpack Compose
    androidTestImplementation("androidx.compose.ui:ui-test-junit4")
    debugImplementation("androidx.compose.ui:ui-tooling")
    debugImplementation("androidx.compose.ui:ui-test-manifest")
}