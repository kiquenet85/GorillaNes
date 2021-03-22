package com.nesgorilla.di

import android.content.Context
import androidx.room.Room
import com.nesgorilla.model.database.GorillaDB

class DataModule(private val appContext: Context) {

    fun provideDB(): GorillaDB {
        return Room.databaseBuilder(appContext, GorillaDB::class.java, "GorillaDB")
            .fallbackToDestructiveMigration().build()
    }
}