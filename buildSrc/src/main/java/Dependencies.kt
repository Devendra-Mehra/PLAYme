object GradleClasspath {
    const val buildGradlePlugin = "com.android.tools.build:gradle:${Versions.build_gradle_version}"
    const val hiltGradlePlugin =
        "com.google.dagger:hilt-android-gradle-plugin:${Versions.hilt_version}"
    const val kotlinGradlePlugin =
        "org.jetbrains.kotlin:kotlin-gradle-plugin:${Versions.kotlin_version}"
}

object AndroidX {
    // ViewModel
    const val lifeCycleViewModel =
        "androidx.lifecycle:lifecycle-viewmodel-ktx:${Versions.lifecycle_version}"

    // optional - ReactiveStreams support for LiveData
    const val lifeCycleLiveDataStreams =
        "androidx.lifecycle:lifecycle-livedata-ktx:${Versions.lifecycle_version}"

    //App Compat
    const val appCompat = "androidx.appcompat:appcompat:${Versions.app_compat_version}"

    //Annotation
    const val annotations = "androidx.annotation:annotation:${Versions.support_lib_version}"
    const val androidKtxCore = "androidx.core:core-ktx:${Versions.ktx_version}"
    const val constraintLayout =
        "androidx.constraintlayout:constraintlayout:${Versions.constraint_layout_version}"
    const val activityKtx = "androidx.activity:activity-ktx:${Versions.activity_ktx}"
}


object Google {
    //Material design
    const val material = "com.google.android.material:material:${Versions.material_version}"

    const val hiltAndroidSupport = "com.google.dagger:hilt-android:${Versions.hilt_version}"
    const val hiltAndroidCompiler =
        "com.google.dagger:hilt-android-compiler:${Versions.hilt_version}"
    const val hiltViewModelAndroidSupport =
        "androidx.hilt:hilt-lifecycle-viewmodel:${Versions.hilt_view_model_version}"
    const val hiltViewModeAndroidCompiler =
        "androidx.hilt:hilt-compiler:${Versions.hilt_view_model_version}"

}

object JetBrains {
    const val kotlinStdLib = "org.jetbrains.kotlin:kotlin-stdlib:${Versions.kotlin_version}"
}

object Android {
    const val exoplayer = "com.google.android.exoplayer:exoplayer:${Versions.exoplayer}"
}


object Testing {
    const val jUnit = "junit:junit:${Versions.j_unit}"
    const val mockitoCore = "org.mockito:mockito-core:${Versions.mockito_core}"
    const val mockitoNhaarman = "com.nhaarman:mockito-kotlin:${Versions.mockito_nhaarman}"
    const val mockitoInline = "org.mockito:mockito-inline:${Versions.mockito_inline}"
    const val androidXTestRunner = "androidx.test:runner:${Versions.android_x_test_runner}"
    const val androidXTestCore = "androidx.test:core:${Versions.android_x_test_core}"
    const val coreTesting = "android.arch.core:core-testing:${Versions.core_testing}"
}



