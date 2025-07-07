package com.stratizen.core.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.stratizen.core.datastore.*
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

/**
 * ViewModel for managing the application's theme mode and typography preferences.
 *
 * Listens to preferences stored in DataStore and exposes them as StateFlow.
 * Supports theme mode, font size, and font family selection.
 */
class ThemeViewModel(application: Application) : AndroidViewModel(application) {

    private val preferenceManager = ThemePreferenceManager(application.applicationContext)

    // Backing state for the currently active theme
    private val _themeMode = MutableStateFlow(ThemeMode.SYSTEM)
    val themeMode: StateFlow<ThemeMode> = _themeMode.asStateFlow()

    // ✅ Font size preference (SMALL, MEDIUM, LARGE)
    val fontSize: StateFlow<FontSize> = preferenceManager.fontSizeFlow
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(), FontSize.MEDIUM)

    // ✅ Font family preference (DEFAULT, ALICE)
    val fontFamily: StateFlow<FontFamilyChoice> = preferenceManager.fontFamilyFlow
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(), FontFamilyChoice.DEFAULT)

    init {
        // Observe theme mode changes from DataStore
        viewModelScope.launch {
            preferenceManager.themeFlow.collectLatest { savedTheme ->
                _themeMode.value = savedTheme
            }
        }
    }

    /**
     * Persists the selected theme mode into DataStore.
     */
    fun setTheme(mode: ThemeMode) {
        viewModelScope.launch {
            preferenceManager.saveTheme(mode)
        }
    }

    /**
     * Persists the selected font size into DataStore.
     *
     * @param size The [FontSize] selected by the user.
     */
    fun updateFontSize(size: FontSize) {
        viewModelScope.launch {
            preferenceManager.setFontSize(size)
        }
    }

    /**
     * Persists the selected font family into DataStore.
     *
     * @param choice The [FontFamilyChoice] selected by the user.
     */
    fun updateFontFamily(choice: FontFamilyChoice) {
        viewModelScope.launch {
            preferenceManager.setFontFamily(choice)
        }
    }
}
