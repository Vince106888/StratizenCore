package com.stratizen.core.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "xp_table")
data class Xp(
    @PrimaryKey val id: Int = 1, // Singleton record
    val points: Int = 0,
    val level: Int = 1
)