package com.stratizen.core.repository

import androidx.lifecycle.LiveData
import com.stratizen.core.data.db.EventDao
import com.stratizen.core.data.model.Event

/**
 * Repository layer that provides a clean API for accessing Event data.
 * Acts as an abstraction between the ViewModel and the Room DAO.
 *
 * @property eventDao Data access object for Event table
 */
class EventRepository(private val eventDao: EventDao) {

    /**
     * Fetches all events sorted by timestamp (LiveData for reactive UI updates).
     */
    fun getAllEvents(): LiveData<List<Event>> = eventDao.getAllEvents()

    /**
     * Retrieves events belonging to a specific group.
     *
     * @param group Group name (e.g., "Class", "Clubs")
     * @return LiveData list of events in that group
     */
    fun getEventsByGroup(group: String): LiveData<List<Event>> =
        eventDao.getEventsByGroup(group)

    /**
     * Inserts a new event into the database (replaces on conflict).
     *
     * @param event Event object to insert
     */
    suspend fun insert(event: Event) {
        eventDao.insert(event)
    }

    /**
     * Inserts an event and returns its auto-generated ID.
     *
     * @param event Event to insert
     * @return ID of the newly inserted event
     */
    suspend fun insertAndReturnId(event: Event): Int {
        return eventDao.insert(event).toInt()
    }

    /**
     * Deletes the specified event.
     *
     * @param event Event to delete
     */
    suspend fun delete(event: Event) {
        eventDao.delete(event)
    }

    /**
     * Updates the given event in the database.
     *
     * @param event Modified event object
     */
    suspend fun update(event: Event) {
        eventDao.update(event)
    }

    /**
     * Retrieves a single event by its ID.
     *
     * @param id Event ID
     * @return LiveData containing the matching event or null
     */
    fun getEventById(id: Int): LiveData<Event?> = eventDao.getEventById(id)
}
