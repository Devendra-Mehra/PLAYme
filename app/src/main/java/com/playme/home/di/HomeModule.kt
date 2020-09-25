package com.playme.home.di

import android.content.Context
import android.content.SharedPreferences
import androidx.recyclerview.widget.PagerSnapHelper
import com.playme.core.PersistenceContract
import com.playme.core.PersistenceImpl
import com.playme.home.model.HomeModel
import com.playme.home.model.LocalStorageRepoImpl
import com.playme.home.model.HomeContract
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent
import dagger.hilt.android.qualifiers.ApplicationContext

@Module
@InstallIn(ActivityComponent::class)
class HomeModule {

    @Provides
    fun provideHomeModel(
        homeContractRepository: HomeContract.Repository,
        persistenceContract: PersistenceContract
    ): HomeModel {
        return HomeModel(homeContractRepository, persistenceContract)
    }

    @Provides
    fun provideLocalStorageRepoImpl(@ApplicationContext context: Context): HomeContract.Repository {
        return LocalStorageRepoImpl(context)
    }

    @Provides
    fun providePersistenceImpl(sharedPreferences: SharedPreferences): PersistenceContract {
        return PersistenceImpl(sharedPreferences)
    }

    @Provides
    fun providePagerSnapHelper(): PagerSnapHelper {
        return  PagerSnapHelper()
    }




}