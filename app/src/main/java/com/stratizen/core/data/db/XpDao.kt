package com.stratizen.core.data.db

import androidx.room.*
import com.stratizen.core.data.model.Xp
import kotlinx.coroutines.flow.Flow

/**
 * Data Access Object (DAO) for managing XP (experience points) data.
 *
 * This interface defines methods to observe, insert, and update the user's XP record.
 */
@Dao
interface XpDao {

    /**
     * Retrieves the XP record with a fixed ID of 1.
     *
     * Used when there's only one XP record representing a global or user-level state.
     *
     * @return A Flow that emits the current XP data reactively on change.
     */
    @Query("SELECT * FROM xp_table WHERE id = 1")
    fun getXp(): Flow<Xp?>

    /**
     * Inserts a new XP record into the database.
     *
     * If a record with the same ID already exists, it will be replaced.
     *
     * @param xp The XP object to insert.
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertXp(xp: Xp)

    /**
     * Updates an existing XP record in the database.
     *
     * @param xp The XP object with updated values.
     */
    @Update
    suspend fun updateXp(xp: Xp)
}
