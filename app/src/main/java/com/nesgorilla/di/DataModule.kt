package com.nesgorilla.di

import android.content.Context
import androidx.room.Room
import com.nesgorilla.model.database.GorillaDB

class DataModule(appContext: Context) {

    private val roomDB = Room.databaseBuilder(appContext, GorillaDB::class.java, "GorillaDB")
        .fallbackToDestructiveMigration().build()

    fun provideDB(): GorillaDB {
        return roomDB
    }
}