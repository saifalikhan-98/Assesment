package com.khan.assesment.utils

import com.khan.assesment.Constants.Companion.BASE_URL
import com.khan.assesment.apis.ApiBuilder
import com.khan.assesment.Constants.Companion.FACEBOOK_URL
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

class RetroFitInstance {

    companion object{
        private val retrofit by lazy {

            val httploggers =  HttpLoggingInterceptor()
            httploggers.setLevel(HttpLoggingInterceptor.Level.BODY)

            val client = OkHttpClient.Builder()
                .connectTimeout(2000, TimeUnit.MILLISECONDS)
                .addInterceptor(httploggers)
                .build()


            Retrofit.Builder()
                .baseUrl("http://65.2.9.217:5000/")
                .addConverterFactory(GsonConverterFactory.create())
                .client(client)
                .build()

        }

        val api by lazy{
            retrofit.create(ApiBuilder::class.java)
        }
    }
}