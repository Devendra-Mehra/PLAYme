package com.playme.di

import android.content.Context
import android.content.SharedPreferences
import com.playme.utils.Constant.MY_PREFERENCES
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Singleton


@Module
@InstallIn(ApplicationComponent::class)
object SharedPreferencesModule {

    @Provides
    @Singleton
    fun providesPreference(@ApplicationContext context: Context): SharedPreferences {
        return context.getSharedPreferences(MY_PREFERENCES, Context.MODE_PRIVATE)
    }
}