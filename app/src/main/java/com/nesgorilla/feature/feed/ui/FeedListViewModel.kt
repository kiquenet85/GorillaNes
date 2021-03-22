package com.nesgorilla.feature.feed.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import com.nesgorilla.base.BaseCoroutineViewModel
import com.nesgorilla.base.error.ErrorHandler
import com.nesgorilla.feature.feed.use_case.FeedListState
import com.nesgorilla.feature.feed.use_case.LoadFeedUC
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class FeedListViewModel(
    private val state: SavedStateHandle,
    errorHandler: ErrorHandler,
    private val loadFeedUC: LoadFeedUC,
) : BaseCoroutineViewModel(errorHandler) {

    init {
        loadFeed()
    }

    private val stateScreen = MutableLiveData<FeedListState>()
    fun getScreenState(): LiveData<FeedListState> = stateScreen

    fun loadFeed() {
        viewModelScope.launch(errorHandler) {
            loadFeedUC.execute().collect {
                stateScreen.value = it
            }
        }
    }
}

