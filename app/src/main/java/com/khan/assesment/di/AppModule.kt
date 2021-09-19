package com.khan.assesment.di

import android.content.Context
import com.khan.assesment.BaseClass
import com.khan.assesment.dataRepo.CommonRepo
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.internal.managers.ApplicationComponentManager
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {


    @Singleton
    @Provides
    fun provideApplication(@ApplicationContext app : Context): BaseClass{
        return app as BaseClass
    }


    @Singleton
    @Provides
    fun provideCommonRepo():CommonRepo{
        return CommonRepo()
    }

}