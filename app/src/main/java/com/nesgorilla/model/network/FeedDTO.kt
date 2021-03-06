package com.nesgorilla.model.network

import com.google.gson.annotations.Expose

data class FeedDTO(
    @Expose var id: String? = null,
    @Expose var first_name: String? = null,
    @Expose var last_name: String? = null,
    @Expose var post_body: String? = null,
    @Expose var unix_timestamp: String? = null
)