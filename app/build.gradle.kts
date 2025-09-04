plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    id("com.google.devtools.ksp")
    id("kotlin-parcelize")
    id("com.google.dagger.hilt.android")
}

android {

    buildFeatures {
        viewBinding = true
    }

    lint {
        abortOnError = false
    }
    namespace = "com.iti.tastytrail"
    compileSdk = 36

    defaultConfig {
        applicationId = "com.iti.tastytrail"
        minSdk = 26
        targetSdk = 36
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    kotlinOptions {
        jvmTarget = "11"
    }
}

dependencies {

// Hilt
    implementation("com.google.dagger:hilt-android:2.56.2")
    ksp("com.google.dagger:hilt-compiler:2.56.2")

// Hilt Navigation
    implementation("androidx.hilt:hilt-navigation-compose:1.2.0")
    implementation("androidx.hilt:hilt-navigation-fragment:1.2.0")

// Hilt extension compiler (if needed)
    ksp("androidx.hilt:hilt-compiler:1.2.0")


    // Room
    implementation ("androidx.room:room-runtime:2.6.0")
    implementation( "androidx.room:room-ktx:2.6.0")
    ksp ("androidx.room:room-compiler:2.6.0")

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    implementation(libs.androidx.activity)
    implementation(libs.androidx.constraintlayout)

    implementation(libs.androidx.databinding.adapters)
    implementation(libs.androidx.navigation.runtime.ktx)
    implementation(libs.androidx.navigation.fragment.ktx)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    implementation("com.squareup.picasso:picasso:2.8")
    implementation("com.squareup.moshi:moshi-kotlin:1.15.2")
    implementation("com.squareup.retrofit2:retrofit:2.9.0")
    implementation("com.squareup.retrofit2:converter-moshi:2.9.0")
    implementation("com.squareup.okhttp3:logging-interceptor:4.11.0")
    val epoxyVersion = "5.1.4"
    implementation("com.airbnb.android:epoxy:${epoxyVersion}")
    implementation("com.google.android.material:material:1.9.0")
    ksp ("com.airbnb.android:epoxy-processor:$epoxyVersion")
}