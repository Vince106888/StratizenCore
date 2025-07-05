package com.stratizen.core.data.db

import androidx.room.*
import com.stratizen.core.data.model.Xp
import kotlinx.coroutines.flow.Flow

@Dao
interface XpDao {
    @Query("SELECT * FROM xp_table WHERE id = 1")
    fun getXp(): Flow<Xp?>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertXp(xp: Xp)

    @Update
    suspend fun updateXp(xp: Xp)
}