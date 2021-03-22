package com.nesgorilla.model.database.entity

import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    indices = [Index(value = ["id"], unique = true)]
)
data class Account(
    @PrimaryKey
    var id: String,
    val name: String
)
