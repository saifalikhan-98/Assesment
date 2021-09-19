package com.khan.assesment.network

import com.khan.assesment.Constants
import com.khan.assesment.network.apis.ApiBuilder
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

class RetrofitForFaceBook {
    companion object{
        private val retrofit by lazy {

            val httploggers =  HttpLoggingInterceptor()
            httploggers.setLevel(HttpLoggingInterceptor.Level.BODY)

            val client = OkHttpClient.Builder()
                .connectTimeout(2000, TimeUnit.MILLISECONDS)
                .addInterceptor(httploggers)
                .build()


            Retrofit.Builder()
                .baseUrl(Constants.FACEBOOK_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(client)
                .build()

        }

        val api by lazy{
            retrofit.create(ApiBuilder::class.java)
        }
    }
}