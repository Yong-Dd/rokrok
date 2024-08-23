plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.jetbrains.kotlin.android)
    alias(libs.plugins.dagger.hilt.android)
    alias(libs.plugins.google.devtools.ksp)
}

android {
    namespace = "com.yongdd.remote"
    compileSdk = 34

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

    testOptions.unitTests.isIncludeAndroidResources = true
}

dependencies {

    implementation(libs.hilt.android)
    ksp(libs.hilt.android.compiler)

    implementation(libs.gson)

    implementation(platform(libs.firebase.bom))
    implementation(libs.bundles.firebase.base)
    implementation(libs.bundles.firebase.server)

    implementation(project(":core:common"))

    /** test **/
    testImplementation(libs.hilt.android.testing)
    kspTest(libs.hilt.android.compiler)
    testImplementation(libs.bundles.test.base)

    testImplementation(project(":test"))
}