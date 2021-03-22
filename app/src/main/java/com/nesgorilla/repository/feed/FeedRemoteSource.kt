package com.nesgorilla.repository.feed

import com.nesgorilla.model.network.FeedResponse

interface FeedRemoteSource {
    suspend fun getAll(query: String): FeedResponse
}