plugins {
    id(AppPlugins.androidApplication)
    kotlin(AppPlugins.kotlinAndroid)
    kotlin(AppPlugins.kotlinAnnotationProcessor)
    id(AppPlugins.hilt)
}

android {
    compileSdk = AppConfigs.compileSdk
    ndkVersion = AppConfigs.ndkVersion

    defaultConfig {
        applicationId = AppConfigs.applicationId
        minSdk = AppConfigs.minSdk
        targetSdk = AppConfigs.targetSdk
        versionCode = AppConfigs.versionCode
        versionName = AppConfigs.versionName

        testInstrumentationRunner = AppConfigs.testInstrumentationRunner
        vectorDrawables {
            useSupportLibrary = true
        }
    }

    buildTypes {
        getByName("debug") {
            isDebuggable = true
            isMinifyEnabled = !isDebuggable
            isShrinkResources = !isDebuggable
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
        }
        getByName("release") {
            isDebuggable = false
            isMinifyEnabled = !isDebuggable
            isShrinkResources = !isDebuggable
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
        }
    }
    flavorDimensions.add(AppConfigs.dimension)
    productFlavors {
        create("staging") {
            applicationIdSuffix = ".staging"
            dimension = AppConfigs.dimension
        }

        create("production") {
            dimension = AppConfigs.dimension
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
        kotlinCompilerExtensionVersion = compose_version
    }
    packagingOptions {
        resources {
            excludes.add("/META-INF/{AL2.0,LGPL2.1}")
        }
    }
    kapt {
        arguments {
            arg("room.schemaLocation", "$projectDir/schemas")
        }
    }
    externalNativeBuild {
        cmake {
            path("CMakeLists.txt")
            version = AppConfigs.cMakeVersion
        }
    }
}

dependencies {
    implementation(Dependencies.appLibraries)
    kapt(Dependencies.appLibrariesProcessors)
    testImplementation(Dependencies.testLibraries)
    androidTestImplementation(Dependencies.androidTestLibraries)
    debugImplementation(Dependencies.debugFlavouredLibraries)
}