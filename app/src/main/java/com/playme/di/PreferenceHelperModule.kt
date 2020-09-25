package com.playme.di

import android.content.Context
import android.content.SharedPreferences
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Singleton

private const val MY_PREFERENCES = "MY_PREFERENCES"


@Module
@InstallIn(ApplicationComponent::class)
object PreferenceHelperModule {

    @Provides
    @Singleton
    fun providesPreferenceHelper(sharedPreferences: SharedPreferences): PreferenceHelper =
        PreferenceHelper(sharedPreferences)

    @Provides
    @Singleton
    fun providesPreference(@ApplicationContext context: Context): SharedPreferences {
        return context.getSharedPreferences(MY_PREFERENCES, Context.MODE_PRIVATE)

    }
}