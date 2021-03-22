package com.nesgorilla.model.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.nesgorilla.model.database.dao.AccountDao
import com.nesgorilla.model.database.dao.FeedDao
import com.nesgorilla.model.database.entity.Account
import com.nesgorilla.model.database.entity.Feed

@Database(
    entities = [
        Account::class, Feed::class
    ], version = 1
)
abstract class GorillaDB : RoomDatabase() {

    abstract fun userDAO(): FeedDao
    abstract fun accountDAO(): AccountDao
}