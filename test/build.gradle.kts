plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.jetbrains.kotlin.android)
    alias(libs.plugins.dagger.hilt.android)
    alias(libs.plugins.google.devtools.ksp)
}

android {
    namespace = "com.yongdd.test"

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
    implementation(libs.hilt.android.testing)
    ksp(libs.hilt.android.compiler)

    implementation(platform(libs.compose.bom))
    implementation(libs.bundles.compose)

    implementation(platform(libs.firebase.bom))
    implementation(libs.bundles.firebase.server)
    implementation(libs.datastore.preferences)
    implementation(libs.bundles.room)
    ksp(libs.room.compiler)

    implementation(libs.kotlinx.coroutines.test)

    implementation(project(":core:common"))
    implementation(project(":data"))
    implementation(project(":di:injectRepository"))
    implementation(project(":domain:model"))
    implementation(project(":domain:interfaceRepository"))
}