package com.nesgorilla.repository.feed

import androidx.room.withTransaction
import com.nesgorilla.model.database.GorillaDB
import com.nesgorilla.model.database.entity.Feed
import com.nesgorilla.util.Optional
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*

class FeedLocalSourceImp(private val db: GorillaDB) : FeedLocalSource {

    override suspend fun createOrUpdate(accountId: String, items: List<Feed>): Boolean {
        val oldItemsId = db.userDAO().getAll(accountId).first().map { it.id }
        val newItemsId = mutableListOf<String>()
        db.withTransaction {
            items.forEach { toInsert ->
                newItemsId.add(toInsert.id)
                createOrUpdate(accountId, toInsert)
            }

            oldItemsId.forEach { oldItemId ->
                if (!newItemsId.contains(oldItemId)) {
                    db.userDAO().delete(oldItemId)
                }
            }
        }
        return true
    }

    override suspend fun createOrUpdate(accountId: String, item: Feed): Boolean {
        if (db.userDAO().update(entityToInsert = item) == 0) {
            db.userDAO().insert(item)
        }
        return true
    }

    override fun getAll(accountId: String): Flow<List<Feed>> {
        return db.userDAO().getAll(accountId)
            .filterNotNull()
            .flowOn(Dispatchers.Default)
            .distinctUntilChanged()
            .conflate()
    }

    override fun getById(id: String): Flow<Optional<Feed>> {
        return db.userDAO().getById(id)
            .map { if (it == null) Optional.None else Optional.Some(it) }
            .flowOn(Dispatchers.Default)
            .distinctUntilChanged()
            .conflate()
    }

    override suspend fun deleteById(accountId: String, id: String): Int {
        return db.userDAO().delete(id)
    }

    override suspend fun deleteAll(accountId: String, items: List<String>): Int {
        var deletions = 0
        db.withTransaction {
            items.forEach { elementId ->
                deletions = db.userDAO().delete(elementId)
            }
        }
        return deletions
    }
}