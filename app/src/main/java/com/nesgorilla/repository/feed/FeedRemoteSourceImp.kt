package com.nesgorilla.repository.feed

import com.nesgorilla.model.network.FeedAPI
import com.nesgorilla.model.network.FeedResponse

class FeedRemoteSourceImp constructor(private val feedAPI: FeedAPI) : FeedRemoteSource {

    override suspend fun getAll(query: String): FeedResponse {
        return feedAPI.getJobSitesByAccountId()
    }
}