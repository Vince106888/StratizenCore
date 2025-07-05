package com.stratizen.core.data.db

import androidx.lifecycle.LiveData
import androidx.room.*
import com.stratizen.core.data.model.Event

@Dao
interface EventDao {

    @Query("SELECT * FROM events ORDER BY timestamp ASC")
    fun getAllEvents(): LiveData<List<Event>>

    @Query("SELECT * FROM events WHERE `group` = :group ORDER BY timestamp ASC")
    fun getEventsByGroup(group: String): LiveData<List<Event>>

    @Query("SELECT * FROM events WHERE id = :id")
    fun getEventById(id: Int): LiveData<Event?>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(event: Event): Long

    @Delete
    suspend fun delete(event: Event)

    @Update
    suspend fun update(event: Event)
}