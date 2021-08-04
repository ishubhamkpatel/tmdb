import org.gradle.api.artifacts.dsl.DependencyHandler

const val compose_version = "1.0.0"

object BuildPlugins {
    const val gradle = "com.android.tools.build:gradle:${Versions.gradle}"
    const val kotlin = "org.jetbrains.kotlin:kotlin-gradle-plugin:${Versions.kotlin}"
    const val hilt = "com.google.dagger:hilt-android-gradle-plugin:${Versions.hilt}"
}

object AppPlugins {
    const val androidApplication = "com.android.application"
    const val kotlinAndroid = "android"
    const val kotlinAnnotationProcessor = "kapt"
    const val hilt = "dagger.hilt.android.plugin"
}

object AppConfigs {
    const val compileSdk = 31
    const val applicationId = "dev.shubhampatel.tmdb"
    const val minSdk = 21
    const val targetSdk = 31
    const val versionCode = 1
    const val versionName = "1.0.0"
    const val testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    const val dimension = "environment"

    const val ndkVersion = "22.1.7171670"
    const val cMakeVersion = "3.10.2"
}

private object Versions {
    const val gradle = "7.0.0"
    const val kotlin = "1.5.10"
    const val coreKtx = "1.6.0"
    const val appCompat = "1.3.1"
    const val material = "1.4.0"
    const val lifecycle = "2.3.1"
    const val activityCompose = "1.3.0"
    const val navigationCompose = "2.4.0-alpha06"
    const val hilt = "2.38.1"
    const val coroutines = "1.5.1"
    const val retrofit = "2.9.0"
    const val okhttp = "4.9.0"
    const val dataStore = "1.0.0-rc02"
    const val room = "2.3.0"
    const val gson = "2.8.7"
    const val coil = "1.3.1"
    const val jUnit = "4.13.2"
    const val extJUnit = "1.1.3"
    const val espressoCore = "3.4.0"
}

object Dependencies {
    // app dependencies
    private const val coreKtx = "androidx.core:core-ktx:${Versions.coreKtx}"
    private const val appCompat = "androidx.appcompat:appcompat:${Versions.appCompat}"
    private const val material = "com.google.android.material:material:${Versions.material}"
    private const val composeUi = "androidx.compose.ui:ui:${compose_version}"
    private const val composeMaterial = "androidx.compose.material:material:${compose_version}"
    private const val composeUiToolingPreview = "androidx.compose.ui:ui-tooling-preview:${compose_version}"
    private const val lifecycle = "androidx.lifecycle:lifecycle-runtime-ktx:${Versions.lifecycle}"
    private const val activityCompose = "androidx.activity:activity-compose:${Versions.activityCompose}"

    private const val navigationCompose = "androidx.navigation:navigation-compose:${Versions.navigationCompose}"
    private const val hiltAndroid = "com.google.dagger:hilt-android:${Versions.hilt}"
    private const val hiltAndroidCompiler = "com.google.dagger:hilt-android-compiler:${Versions.hilt}"
    private const val hiltCompiler = "com.google.dagger:hilt-compiler:${Versions.hilt}"
    private const val coroutinesCore = "org.jetbrains.kotlinx:kotlinx-coroutines-core:${Versions.coroutines}"
    private const val coroutinesAndroid = "org.jetbrains.kotlinx:kotlinx-coroutines-android:${Versions.coroutines}"
    private const val retrofit = "com.squareup.retrofit2:retrofit:${Versions.retrofit}"
    private const val retrofitGsonConverter = "com.squareup.retrofit2:converter-gson:${Versions.retrofit}"
    private const val okhttp = "com.squareup.okhttp3:okhttp:${Versions.okhttp}"
    private const val okhttpLogger = "com.squareup.okhttp3:logging-interceptor:${Versions.okhttp}"
    private const val dataStoreTyped = "androidx.datastore:datastore:${Versions.dataStore}"
    private const val dataStorePrefs = "androidx.datastore:datastore-preferences:${Versions.dataStore}"
    private const val roomRuntime = "androidx.room:room-runtime:${Versions.room}"
    private const val roomCompiler = "androidx.room:room-compiler:${Versions.room}"
    private const val roomKtx = "androidx.room:room-ktx:${Versions.room}"
    private const val viewModel = "androidx.lifecycle:lifecycle-viewmodel-ktx:${Versions.lifecycle}"
    private const val liveData = "androidx.lifecycle:lifecycle-livedata-ktx:${Versions.lifecycle}"
    private const val lifecycleCompiler = "androidx.lifecycle:lifecycle-compiler:${Versions.lifecycle}"
    private const val gson = "com.google.code.gson:gson:${Versions.gson}"
    private const val coil = "io.coil-kt:coil:${Versions.coil}"

    // test
    private const val jUnit = "junit:junit:${Versions.jUnit}"
    private const val extJUnit = "androidx.test.ext:junit:${Versions.extJUnit}"
    private const val espressoCore = "androidx.test.espresso:espresso-core:${Versions.espressoCore}"
    private const val composeUiTestJUnit = "androidx.compose.ui:ui-test-junit4:${compose_version}"

    // flavour-specific
    private const val composeUiTooling = "androidx.compose.ui:ui-tooling:${compose_version}"

    val appLibraries = arrayListOf<String>().apply {
        add(coreKtx)
        add(appCompat)
        add(material)
        add(composeUi)
        add(composeMaterial)
        add(composeUiToolingPreview)
        add(lifecycle)
        add(activityCompose)
        add(navigationCompose)
        add(hiltAndroid)
        add(coroutinesCore)
        add(coroutinesAndroid)
        add(retrofit)
        add(retrofitGsonConverter)
        add(okhttp)
        add(okhttpLogger)
        add(dataStoreTyped)
        add(dataStorePrefs)
        add(roomRuntime)
        add(roomKtx)
        add(viewModel)
        add(liveData)
        add(gson)
        add(coil)
    }

    val appLibrariesProcessors = arrayListOf<String>().apply {
        add(hiltAndroidCompiler)
        add(hiltCompiler)
        add(roomCompiler)
        add(lifecycleCompiler)
    }

    val testLibraries = arrayListOf<String>().apply {
        add(jUnit)
    }

    val androidTestLibraries = arrayListOf<String>().apply {
        add(extJUnit)
        add(espressoCore)
        add(composeUiTestJUnit)
    }

    val debugFlavouredLibraries = arrayListOf<String>().apply {
        add(composeUiTooling)
    }
}

fun DependencyHandler.implementation(list: List<String>) {
    list.forEach {
        add("implementation", it)
    }
}

fun DependencyHandler.kapt(list: List<String>) {
    list.forEach {
        add("kapt", it)
    }
}

fun DependencyHandler.testImplementation(list: List<String>) {
    list.forEach {
        add("testImplementation", it)
    }
}

fun DependencyHandler.androidTestImplementation(list: List<String>) {
    list.forEach {
        add("androidTestImplementation", it)
    }
}

fun DependencyHandler.debugImplementation(list: List<String>) {
    list.forEach {
        add("debugImplementation", it)
    }
}