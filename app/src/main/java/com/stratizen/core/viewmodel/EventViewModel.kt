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

    fun addEvent(event: Event) = viewModelScope.launch {
        repository.insert(event)
    }

    fun addEventAndReturnId(event: Event, onResult: (Int) -> Unit) {
        viewModelScope.launch {
            val id = repository.insertAndReturnId(event)
            onResult(id)
        }
    }

    fun getEventsByGroup(group: String): LiveData<List<Event>> {
        return repository.getEventsByGroup(group)
    }

    fun delete(event: Event) = viewModelScope.launch {
        repository.delete(event)
    }

    fun update(event: Event) = viewModelScope.launch {
        repository.update(event)
    }

    fun getEventById(id: Int): LiveData<Event?> {
        return repository.getEventById(id)
    }
}
