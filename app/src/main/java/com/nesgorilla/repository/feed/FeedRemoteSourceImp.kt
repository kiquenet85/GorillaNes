package com.nesgorilla.repository.feed

import com.nesgorilla.model.network.FeedAPI
import com.nesgorilla.model.network.FeedDTO

class FeedRemoteSourceImp constructor(private val feedAPI: FeedAPI) : FeedRemoteSource {

    override suspend fun getAll(): List<FeedDTO> {
        return feedAPI.getJobSitesByAccountId()
    }
}