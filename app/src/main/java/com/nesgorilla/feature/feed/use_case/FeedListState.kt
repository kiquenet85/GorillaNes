package com.nesgorilla.feature.feed.use_case

import com.nesgorilla.model.database.entity.Feed

sealed class FeedListState

open class FeedListLoaded(
    val allItems: List<Feed>,
    val filteredItems: List<Feed> = emptyList()
) : FeedListState()

class FeedListSelectedMode(
    allItems: List<Feed>,
    filteredItems: List<Feed> = emptyList()
) : FeedListLoaded(allItems, filteredItems)