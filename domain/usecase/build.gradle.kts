plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.jetbrains.kotlin.android)
    alias(libs.plugins.dagger.hilt.android)
    alias(libs.plugins.google.devtools.ksp)
}

android {
    namespace = "com.yongdd.usecase"

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
}

dependencies {
    implementation(libs.hilt.android)
    ksp(libs.hilt.android.compiler)

    implementation(project(":core:common"))
    implementation(project(":domain:model"))
    implementation(project(":domain:interfaceRepository"))
    implementation(project(":di:injectRepository"))

    /** test **/
    testImplementation(libs.hilt.android.testing)
    ksp(libs.hilt.android.compiler)
    testImplementation(libs.bundles.test.base)

//    testImplementation(project(":test"))
}