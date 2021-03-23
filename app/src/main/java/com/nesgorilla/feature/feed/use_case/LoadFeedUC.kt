package com.nesgorilla.feature.feed.use_case

import com.nesgorilla.repository.feed.FeedGetInfo
import com.nesgorilla.repository.feed.FeedRepository
import com.nesgorilla.util.ACCOUNT_MOCK
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext

class LoadFeedUC(private val feedRepository: FeedRepository) {

    suspend fun execute() = withContext(Dispatchers.Default) {
        feedRepository.getAll(FeedGetInfo(ACCOUNT_MOCK))
            .map { list ->
                FeedListLoaded(list)
            }
    }
}
