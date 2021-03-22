package com.nesgorilla.model.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.nesgorilla.model.database.dao.AccountDAO
import com.nesgorilla.model.database.dao.FeedDAO
import com.nesgorilla.model.database.entity.Account
import com.nesgorilla.model.database.entity.Feed

@Database(
    entities = [
        Account::class, Feed::class
    ], version = 2
)
abstract class GorillaDB : RoomDatabase() {

    abstract fun feedDAO(): FeedDAO
    abstract fun accountDAO(): AccountDAO
}