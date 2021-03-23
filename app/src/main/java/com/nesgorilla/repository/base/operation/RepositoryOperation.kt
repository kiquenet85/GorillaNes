@file:Suppress("UNCHECKED_CAST")

package com.nesgorilla.repository.base.operation

import com.nesgorilla.base.error.ErrorHandler
import com.nesgorilla.repository.base.RepositoryPolicy
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*

interface RepositoryReadOperation<Remote, Local, Info, Return> : RepositoryPolicy<Info> {

    fun getErrorHandler() : ErrorHandler

    suspend fun execute(info: Info): Flow<Return> {
        val emmitRemoteErrors = flow<Return> {
            if (shouldGoRemote(info)) {
                val remoteResult = endpoint(info)
                val transformedResult = transformRemoteResult(remoteResult, info)
                updateDatabase(transformedResult, info)
            }
        }.catch { e ->
            getErrorHandler().report(e)
        }.flowOn(Dispatchers.IO)
        return listOf(readFromDatabase(info), emmitRemoteErrors).merge()
    }

    suspend fun endpoint(info: Info): Remote = info as Remote
    suspend fun transformRemoteResult(remoteData: Remote, info: Info): Local = remoteData as Local
    suspend fun updateDatabase(data: Local, info: Info): Boolean = false
    suspend fun readFromDatabase(info: Info): Flow<Return>
}
