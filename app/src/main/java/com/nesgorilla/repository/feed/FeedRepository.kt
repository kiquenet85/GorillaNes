package com.nesgorilla.repository.feed

import com.nesgorilla.model.database.entity.Feed
import com.nesgorilla.model.network.FeedDTO
import com.nesgorilla.repository.base.operation.RepositoryReadOperation
import com.nesgorilla.util.Optional
import kotlinx.coroutines.flow.Flow

class FeedRepository(
    private val localSource: FeedLocalSource,
    private val remoteSource: FeedRemoteSource
) {

    suspend fun getAll(info: FeedGetInfo): Flow<List<Feed>> {
        val operation = object :
            RepositoryReadOperation<List<FeedDTO>, List<Feed>, FeedGetInfo, List<Feed>> {

            override suspend fun shouldGoRemote(info: FeedGetInfo): Boolean = true

            override suspend fun endpoint(info: FeedGetInfo): List<FeedDTO> {
                return emptyList() //remoteSource.getAll(info.accountId)
            }

            override suspend fun transformRemoteResult(
                remoteData: List<FeedDTO>,
                info: FeedGetInfo
            ): List<Feed> {
                return remoteData.map { Feed(it) }
            }

            override suspend fun updateDatabase(
                data: List<Feed>,
                info: FeedGetInfo
            ): Boolean {
                return localSource.createOrUpdate(info.accountId, data)
            }

            override suspend fun readFromDatabase(info: FeedGetInfo): Flow<List<Feed>> {
                return localSource.getAll(info.accountId)
            }
        }
        return operation.execute(info)
    }

    fun getById(id: String): Flow<Optional<Feed>> {
        return localSource.getById(id)
    }

    suspend fun deleteAll(accountId: String, id: List<String>): Int {
        return localSource.deleteAll(accountId, id)
    }
}

data class FeedGetInfo(
    val accountId: String,
    val requiresRemote: Boolean = false,
    val timeOperation: Long = System.currentTimeMillis()
)