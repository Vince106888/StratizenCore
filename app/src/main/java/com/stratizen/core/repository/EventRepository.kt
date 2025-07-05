package com.stratizen.core.repository

import androidx.lifecycle.LiveData
import com.stratizen.core.data.db.EventDao
import com.stratizen.core.data.model.Event

class EventRepository(private val eventDao: EventDao) {

    fun getAllEvents(): LiveData<List<Event>> = eventDao.getAllEvents()

    fun getEventsByGroup(group: String): LiveData<List<Event>> = eventDao.getEventsByGroup(group)

    suspend fun insert(event: Event) {
        eventDao.insert(event)
    }

    suspend fun insertAndReturnId(event: Event): Int {
        return eventDao.insert(event).toInt()
    }

    suspend fun delete(event: Event) {
        eventDao.delete(event)
    }

    suspend fun update(event: Event) {
        eventDao.update(event)
    }

    fun getEventById(id: Int): LiveData<Event?> = eventDao.getEventById(id)
}
