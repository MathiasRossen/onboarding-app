plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    id("com.google.dagger.hilt.android")
    id("com.google.devtools.ksp")
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
        buildConfigField("String", "API_BASE_URL", "\"https://newsapi.org\"")
        buildConfigField("String", "API_KEY", "\"9bf05c7546e9499f82ea4df0c24ce8d1\"")
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    kotlinOptions {
        jvmTarget = "17"
    }
    buildFeatures {
        compose = true
        buildConfig = true
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
    implementation("androidx.appcompat:appcompat:${Versions.ANDROIDX_APPCOMPAT_VERSION}")

    // Jetpack Compose
    implementation("androidx.compose.ui:ui")
    implementation("androidx.compose.ui:ui-graphics")
    implementation("androidx.compose.ui:ui-tooling-preview")
    implementation("androidx.compose.material3:material3")
    implementation("androidx.compose.material:material")

    // Room
    implementation("androidx.room:room-runtime:${Versions.ROOM_VERSION}")
    annotationProcessor("androidx.room:room-compiler:${Versions.ROOM_VERSION}")
    ksp("androidx.room:room-compiler:${Versions.ROOM_VERSION}")
    implementation("androidx.room:room-rxjava3:${Versions.ROOM_VERSION}")

    // Retrofit
    implementation("com.squareup.retrofit2:retrofit:${Versions.RETROFIT_VERSION}")
    implementation("com.squareup.retrofit2:converter-moshi:${Versions.MOSHI_CONVERTER_VERSION}")
    implementation("com.squareup.retrofit2:adapter-rxjava3:${Versions.RETROFIT_VERSION}")
    implementation("com.squareup.okhttp3:logging-interceptor:${Versions.LOGGING_INTERCEPTOR_VERSION}")

    // RxJava
    implementation("io.reactivex.rxjava3:rxandroid:${Versions.RXANDROID_VERSION}")
    implementation("io.reactivex.rxjava3:rxjava:${Versions.RXJAVA_VERSION}")

    // Hilt
    implementation("com.google.dagger:hilt-android:${Versions.HILT_VERSION}")
    ksp("com.google.dagger:hilt-android-compiler:${Versions.HILT_VERSION}")
    implementation("androidx.hilt:hilt-navigation-compose:${Versions.HILT_NAVIGATION_COMPOSE_VERSION}")

    // DataStore
    implementation("androidx.datastore:datastore-preferences:${Versions.DATASTORE_PREFERENCES_VERSION}")
    implementation("androidx.datastore:datastore-preferences-rxjava3:${Versions.DATASTORE_PREFERENCES_RXJAVA_VERSION}")

    // Joda time
    implementation("joda-time:joda-time:${Versions.JODA_TIME_VERSION}")

    // Coil
    implementation("io.coil-kt:coil-compose:${Versions.COIL_COMPOSE_VERSION}")

    // Test implementations
    testImplementation("junit:junit:${Versions.JUNIT_VERSION}")
    testImplementation("org.mockito:mockito-core:${Versions.MOCKITO_VERSION}")
    testImplementation("org.mockito.kotlin:mockito-kotlin:${Versions.MOCKITO_KOTLIN_VERSION}")
    testImplementation("com.google.dagger:hilt-android-testing:${Versions.HILT_TESTING_VERSION}")
    testImplementation("org.jetbrains.kotlinx:kotlinx-coroutines-test:${Versions.COROUTINES_TEST_VERSION}")
    kspTest("com.google.dagger:hilt-android-compiler:${Versions.HILT_VERSION}")
    androidTestImplementation("androidx.test.ext:junit:${Versions.ANDROIDX_JUNIT_VERSION}")
    androidTestImplementation("androidx.test.espresso:espresso-core:${Versions.ANDROIDX_ESPRESSO_VERSION}")
    androidTestImplementation("androidx.compose.ui:ui-test-junit4")

    // Compose debug implementations
    debugImplementation("androidx.compose.ui:ui-tooling")
    debugImplementation("androidx.compose.ui:ui-test-manifest")
}
