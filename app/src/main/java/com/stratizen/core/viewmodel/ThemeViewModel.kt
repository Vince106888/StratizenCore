package com.stratizen.core.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.stratizen.core.datastore.ThemePreferenceManager
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import com.stratizen.core.datastore.ThemeMode

class ThemeViewModel(application: Application) : AndroidViewModel(application) {

    private val preferenceManager = ThemePreferenceManager(application.applicationContext)

    private val _themeMode = MutableStateFlow(ThemeMode.SYSTEM)
    val themeMode: StateFlow<ThemeMode> = _themeMode.asStateFlow()

    init {
        viewModelScope.launch {
            preferenceManager.themeFlow.collectLatest {
                _themeMode.value = it
            }
        }
    }

    fun setTheme(mode: ThemeMode) {
        viewModelScope.launch {
            preferenceManager.saveTheme(mode)
        }
    }
}
