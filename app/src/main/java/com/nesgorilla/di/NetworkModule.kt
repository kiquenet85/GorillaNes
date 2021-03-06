package com.nesgorilla.di

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

class NetworkModule {
    private var retrofit: Retrofit? = null
    private var gson: Gson? = null

    fun provideDefaultGson(): Gson {
        if (gson == null) {
            gson = GsonBuilder().create()
        }
        return gson!!
    }

    fun provideRetrofit(): Retrofit {
        if (retrofit == null) {
            val okHttpClient = OkHttpClient.Builder()
                .readTimeout(30, TimeUnit.SECONDS)
                .connectTimeout(30, TimeUnit.SECONDS)
                .build()

            // Create a very simple REST adapter which points to the settings base URL.
            retrofit = Retrofit.Builder()
                .baseUrl("https://gl-endpoint.herokuapp.com")
                .addConverterFactory(GsonConverterFactory.create())
                .client(okHttpClient)
                .build()
        }

        return retrofit!!
    }
}