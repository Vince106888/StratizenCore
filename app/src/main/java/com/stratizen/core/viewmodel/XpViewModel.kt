package com.stratizen.core.viewmodel

import android.app.Application
import androidx.lifecycle.*
import com.stratizen.core.data.db.AppDatabase
import com.stratizen.core.data.model.Xp
import com.stratizen.core.repository.XpRepository
import kotlinx.coroutines.launch

class XpViewModel(application: Application) : AndroidViewModel(application) {

    private val repository: XpRepository
    val xp: LiveData<Xp?>

    init {
        val xpDao = AppDatabase.getDatabase(application).xpDao()
        repository = XpRepository(xpDao)
        xp = repository.xp.asLiveData()
    }

    fun awardXp(points: Int) = viewModelScope.launch {
        repository.awardXp(points)
    }
}