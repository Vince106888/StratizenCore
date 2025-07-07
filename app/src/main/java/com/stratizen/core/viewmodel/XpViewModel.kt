package com.stratizen.core.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.stratizen.core.data.db.AppDatabase
import com.stratizen.core.data.model.Xp
import com.stratizen.core.repository.XpRepository
import kotlinx.coroutines.launch

/**
 * ViewModel responsible for managing XP (experience points) logic.
 *
 * Provides XP as observable LiveData and allows awarding new XP.
 */
class XpViewModel(application: Application) : AndroidViewModel(application) {

    private val repository: XpRepository
    val xp: LiveData<Xp?> // Exposed to UI to observe XP state

    init {
        val xpDao = AppDatabase.getDatabase(application).xpDao()
        repository = XpRepository(xpDao)
        xp = repository.xp.asLiveData()
    }

    /**
     * Award a given amount of XP points and update the stored XP record.
     */
    fun awardXp(points: Int) = viewModelScope.launch {
        repository.awardXp(points)
    }
}
