package com.stratizen.core.data.db

import androidx.lifecycle.LiveData
import androidx.room.*
import com.stratizen.core.data.model.Event

/**
 * Data Access Object (DAO) for the `Event` entity.
 *
 * Defines all the database operations related to Event records,
 * including queries, insertion, deletion, and updates.
 */
@Dao
interface EventDao {

    /**
     * Fetch all events from the database, ordered by timestamp (ascending).
     *
     * @return LiveData list of all events.
     */
    @Query("SELECT * FROM events ORDER BY timestamp ASC")
    fun getAllEvents(): LiveData<List<Event>>

    /**
     * Fetch events that belong to a specific group.
     *
     * @param group The group identifier to filter events.
     * @return LiveData list of events in the given group, ordered by timestamp.
     */
    @Query("SELECT * FROM events WHERE `group` = :group ORDER BY timestamp ASC")
    fun getEventsByGroup(group: String): LiveData<List<Event>>

    /**
     * Fetch a single event by its unique ID.
     *
     * @param id The ID of the event.
     * @return LiveData of the corresponding event, or null if not found.
     */
    @Query("SELECT * FROM events WHERE id = :id")
    fun getEventById(id: Int): LiveData<Event?>

    /**
     * Insert a new event into the database.
     *
     * If a conflict occurs (e.g. same ID), the existing event is replaced.
     *
     * @param event The Event object to insert.
     * @return The row ID of the newly inserted event.
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(event: Event): Long

    /**
     * Delete a specific event from the database.
     *
     * @param event The Event object to delete.
     */
    @Delete
    suspend fun delete(event: Event)

    /**
     * Update an existing event in the database.
     *
     * @param event The Event object with updated fields.
     */
    @Update
    suspend fun update(event: Event)
}
