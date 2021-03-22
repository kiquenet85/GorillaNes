package com.nesgorilla.model.network

import retrofit2.http.GET

interface FeedAPI {

    @GET("/feed")
    suspend fun getJobSitesByAccountId(): FeedResponse
}