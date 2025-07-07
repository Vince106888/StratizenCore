package com.stratizen.core.viewmodel

import android.app.Application
import androidx.lifecycle.*
import com.stratizen.core.data.db.AppDatabase
import com.stratizen.core.data.model.Event
import com.stratizen.core.repository.EventRepository
import kotlinx.coroutines.launch

class EventViewModel(application: Application) : AndroidViewModel(application) {

    private val repository: EventRepository
    val allEvents: LiveData<List<Event>>

    init {
        val eventDao = AppDatabase.getDatabase(application).eventDao()
        repository = EventRepository(eventDao)
        allEvents = repository.getAllEvents()
    }

    /**
     * Adds an event to the database.
     */
    fun addEvent(event: Event) = viewModelScope.launch {
        repository.insert(event)
    }

    /**
     * Adds an event and returns the new ID via a callback.
     */
    fun addEventAndReturnId(event: Event, onResult: (Int) -> Unit) {
        viewModelScope.launch {
            val id = repository.insertAndReturnId(event)
            onResult(id)
        }
    }

    /**
     * Updates an existing event in the database.
     */
    fun update(event: Event) = viewModelScope.launch {
        repository.update(event)
    }

    /**
     * Deletes an event from the database.
     */
    fun delete(event: Event) = viewModelScope.launch {
        repository.delete(event)
    }

    /**
     * Gets all events matching a given group.
     */
    fun getEventsByGroup(group: String): LiveData<List<Event>> {
        return repository.getEventsByGroup(group)
    }

    /**
     * Retrieves a single event by ID.
     */
    fun getEventById(id: Int): LiveData<Event?> {
        return repository.getEventById(id)
    }
}
