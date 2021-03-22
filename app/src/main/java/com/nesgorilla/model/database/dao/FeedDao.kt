package com.nesgorilla.model.database.dao

import androidx.room.*
import com.nesgorilla.model.database.entity.Feed
import kotlinx.coroutines.flow.Flow

@Dao
interface FeedDao {

    @Query("SELECT * FROM Feed where id = :id")
    fun getById(id: String): Flow<Feed?>

    @Query("SELECT * FROM Feed where accountId = :accountId")
    fun getAll(accountId: String): Flow<List<Feed>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(items: List<Feed>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(entityToInsert: Feed)

    @Update(onConflict = OnConflictStrategy.REPLACE)
    suspend fun update(entityToInsert: Feed): Int

    @Query("DELETE FROM Feed")
    suspend fun deleteAll(): Int

    @Query("DELETE FROM Feed where id = :id")
    suspend fun delete(id: String): Int
}