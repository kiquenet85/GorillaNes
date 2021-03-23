package com.nesgorilla.di.feed

import androidx.savedstate.SavedStateRegistryOwner
import com.nesgorilla.base.error.ErrorHandler
import com.nesgorilla.feature.feed.ui.FeedViewModelFactory
import com.nesgorilla.feature.feed.use_case.LoadFeedUC
import com.nesgorilla.model.database.GorillaDB
import com.nesgorilla.model.network.FeedAPI
import com.nesgorilla.repository.feed.FeedLocalSourceImp
import com.nesgorilla.repository.feed.FeedRemoteSourceImp
import com.nesgorilla.repository.feed.FeedRepository
import retrofit2.Retrofit

class FeedModule(
    private val db: GorillaDB,
    private val retrofit: Retrofit,
    private val errorHandler: ErrorHandler
) {

    fun provideFeedListViewFactory(owner: SavedStateRegistryOwner) =
        FeedViewModelFactory(
            owner,
            errorHandler,
            this
        )

    fun provideFeedRemoteSource() = FeedRemoteSourceImp(provideFeedRemoteAPI())

    fun provideFeedLocalSource() = FeedLocalSourceImp(db)

    fun provideFeedRemoteAPI() = retrofit.create(FeedAPI::class.java)

    fun provideLoadFeedListUC() =
        LoadFeedUC(provideUserRepository())

    fun provideUserRepository() =
        FeedRepository(errorHandler, provideFeedLocalSource(), provideFeedRemoteSource())
}