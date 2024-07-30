plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.jetbrains.kotlin.android)
    alias(libs.plugins.dagger.hilt.android)
    alias(libs.plugins.google.devtools.ksp)
}

android {
    namespace = "com.yongdd.presentation.start"

    compileSdk = libs.versions.compileSdk.get().toInt()
    defaultConfig {
        minSdk = libs.versions.minSdk.get().toInt()
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }

    kotlinOptions {
        jvmTarget = JavaVersion.VERSION_17.toString()
    }

    buildFeatures {
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = libs.versions.kotlinCompilerExtensionVersion.get()
    }

    testOptions.unitTests.isIncludeAndroidResources = true
}

dependencies {
    implementation(libs.hilt.android)
    ksp(libs.hilt.android.compiler)
    implementation(platform(libs.compose.bom))
    implementation(libs.bundles.compose)
    implementation(libs.compose.material3)
    implementation(libs.bundles.coil)

    implementation(project(":core:common"))
    implementation(project(":core:ui"))
    implementation(project(":domain:usecase"))
    implementation(project(":domain:model"))

    /** test **/
    testImplementation(libs.hilt.navigation.compose)
    testImplementation(libs.hilt.android.testing)
    kspTest(libs.hilt.android.compiler)
    testImplementation(libs.bundles.test.base)

    testImplementation(libs.compose.ui.test.junit4)
    debugImplementation(libs.compose.ui.test.manifest)

    testImplementation(project(":di:injectRepository"))
    testImplementation(project(":test"))
}