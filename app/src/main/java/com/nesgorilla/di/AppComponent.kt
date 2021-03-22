package com.nesgorilla.di

import android.content.Context
import com.nesgorilla.manager.ResourceManager
import com.nesgorilla.GorillaApp
import com.nesgorilla.base.error.ErrorHandler
import com.nesgorilla.di.feed.FeedModule

class AppComponent private constructor (private val appContext: Context) {

    private val networkModule: NetworkModule by lazy { NetworkModule() }
    private val dataModule: DataModule =  DataModule(appContext).apply {
        provideDB().query("select 1", null)
    }

    private val feedModule by lazy { FeedModule(dataModule.provideDB(), networkModule.provideRetrofit(), errorHandler) }
    private val errorHandler by lazy { ErrorHandler() }

    fun provideResourceManager() = ResourceManager.getInstance(appContext)

    fun provideNetworkModule(): NetworkModule = networkModule

    fun provideDataModule(): DataModule = dataModule

    fun provideFeedModule(): FeedModule = feedModule

    fun provideErrorHandler(): ErrorHandler = errorHandler

    companion object {
        @Volatile
        private var instance: AppComponent? = null

        @JvmStatic
        fun getInstance() = instance ?: throw IllegalStateException("AppComponent was not initialized")

        fun initialize(gorillaApp: GorillaApp) = AppComponent(gorillaApp).also { instance = it }
    }
}