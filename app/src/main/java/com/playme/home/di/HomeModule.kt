package com.playme.home.di

import android.content.Context
import android.content.SharedPreferences
import androidx.recyclerview.widget.PagerSnapHelper
import com.playme.core.PersistenceContract
import com.playme.core.PersistenceImpl
import com.playme.home.model.HomeModel
import com.playme.home.model.LocalStorageRepoImpl
import com.playme.home.model.HomeContract
import com.playme.home.model.HomeModelImpl
import com.playme.home.ui.MediaAdapter
import com.playme.utils.Constant.FIXED_THREAD_POOL
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors


@Module
@InstallIn(ActivityComponent::class)
class HomeModule {

    @Provides
    fun provideHomeModel(
        homeContractRepository: HomeContract.Repository,
        persistenceContract: PersistenceContract,
        executorService: ExecutorService
    ): HomeModel {
        return HomeModelImpl(homeContractRepository, persistenceContract, executorService)
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
        return PagerSnapHelper()
    }

    @Provides
    fun provideMediaAdapter() = MediaAdapter()

    @Provides
    fun provideThread(): ExecutorService = Executors.newFixedThreadPool(FIXED_THREAD_POOL)

}