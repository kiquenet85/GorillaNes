package com.nesgorilla.repository.feed

import com.nesgorilla.model.database.entity.Feed
import com.nesgorilla.util.Optional
import kotlinx.coroutines.flow.Flow

interface FeedLocalSource {

    suspend fun createOrUpdate(accountId: String, items: List<Feed>): Boolean

    suspend fun createOrUpdate(accountId: String, items: Feed): Boolean

    fun getAll(accountId: String): Flow<List<Feed>>

    fun getById(id: String): Flow<Optional<Feed>>

    suspend fun deleteById(accountId: String, id: String): Int

    suspend fun deleteAll(accountId: String, items: List<String>): Int
}