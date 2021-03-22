package com.nesgorilla.repository.feed

import com.nesgorilla.model.network.FeedDTO

interface FeedRemoteSource {
    suspend fun getAll(): List<FeedDTO>
}