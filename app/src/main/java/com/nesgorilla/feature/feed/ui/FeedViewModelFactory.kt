package com.nesgorilla.feature.feed.ui

import android.os.Bundle
import androidx.lifecycle.AbstractSavedStateViewModelFactory
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.savedstate.SavedStateRegistryOwner
import com.nesgorilla.base.error.ErrorHandler
import com.nesgorilla.di.feed.FeedModule

@Suppress("UNCHECKED_CAST")
class FeedViewModelFactory(
    owner: SavedStateRegistryOwner,
    private val errorHandler: ErrorHandler,
    private val feedModule: FeedModule,
    defaultArgs: Bundle? = null
) : AbstractSavedStateViewModelFactory(owner, defaultArgs) {

    override fun <T : ViewModel?> create(
        key: String,
        modelClass: Class<T>,
        handle: SavedStateHandle
    ): T {
        return with(modelClass) {
            when {
                isAssignableFrom(FeedListViewModel::class.java) ->
                    FeedListViewModel(
                        handle,
                        errorHandler,
                        feedModule.provideLoadFeedListUC()
                    )

                else -> throw IllegalArgumentException("Unknown ViewModel class in Locations: ${modelClass.name}")
            }
        } as T
    }
}