package com.khan.assesment.di

import android.content.Context
import com.khan.assesment.BaseClass
import com.khan.assesment.BuildConfig
import com.khan.assesment.Constants.Companion.BASE_URL
import com.khan.assesment.dataRepo.CommonRepo
import com.khan.assesment.network.apis.ApiBuilder
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.internal.managers.ApplicationComponentManager
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create
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