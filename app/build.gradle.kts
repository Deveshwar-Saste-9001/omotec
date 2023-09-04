plugins {
    id("com.android.application")
    id("com.google.gms.google-services")
}


android {
    namespace = "com.example.attendancesystem_omotec"
    compileSdk = 33

    defaultConfig {
        applicationId = "com.example.attendancesystem_omotec"
        minSdk = 26
        targetSdk = 31
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
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
    buildFeatures {
        viewBinding = true
    }
}

dependencies {

    implementation("androidx.appcompat:appcompat:1.6.1")
    implementation("com.google.android.material:material:1.8.0")
    implementation("androidx.constraintlayout:constraintlayout:2.1.4")
    implementation("androidx.lifecycle:lifecycle-livedata-ktx:2.6.1")
    implementation("androidx.lifecycle:lifecycle-viewmodel-ktx:2.6.1")
    implementation("androidx.navigation:navigation-fragment:2.5.3")
    implementation("androidx.navigation:navigation-ui:2.5.3")
    implementation("de.hdodenhof:circleimageview:3.0.1")
    implementation("com.google.firebase:firebase-auth:22.1.1")
    implementation("com.google.firebase:firebase-database:20.2.2")
    implementation("com.google.firebase:firebase-firestore:24.7.0")
    implementation("com.google.firebase:firebase-storage:20.2.1")
    implementation("org.apache.poi:poi:5.2.3")
    implementation("org.apache.poi:poi-ooxml:5.2.3")

    implementation("com.github.bumptech.glide:glide:4.8.0")
    implementation("com.google.firebase:firebase-inappmessaging:20.3.5")
    implementation("com.github.bumptech.glide:glide:5.0.0-rc01")
    annotationProcessor ("com.github.bumptech.glide:compiler:4.8.0")
    implementation("com.google.firebase:firebase-storage:20.2.1")
    implementation("com.squareup.picasso:picasso:2.71828")

    testImplementation("junit:junit:4.13.2")
    implementation("com.github.smarteist:autoimageslider:1.4.0-appcompat")
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")
    implementation(platform("org.jetbrains.kotlin:kotlin-bom:1.8.0"))
    implementation("com.google.firebase:firebase-analytics")
    implementation(platform("com.google.firebase:firebase-bom:32.2.2"))
    implementation("com.github.rey5137:material:1.1.0")
    implementation("io.github.pilgr:paperdb:2.7.2")

}
