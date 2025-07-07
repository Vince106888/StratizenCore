package com.stratizen.core.datastore

import android.content.Context
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

// ✅ Extension property to easily access DataStore from any Context
val Context.dataStore by preferencesDataStore(name = "settings")

/**
 * Handles theme mode preferences using Jetpack DataStore.
 *
 * This class provides methods to persist and observe the current theme mode.
 * It bridges between the UI (ThemeMode enum) and storage layer (DataStore).
 */
class ThemePreferenceManager(private val context: Context) {

    companion object {
        // 🔑 Unique key used to store the selected theme mode
        private val THEME_KEY = stringPreferencesKey("theme_mode")

        // ✅ Keys for storing font preferences
        private val FONT_SIZE_KEY = stringPreferencesKey("font_size")
        private val FONT_FAMILY_KEY = stringPreferencesKey("font_family")
    }

    /**
     * A Flow that emits the current theme mode.
     *
     * - Automatically updates whenever the theme preference changes.
     * - Maps stored string value to corresponding [ThemeMode] enum.
     * - Defaults to [ThemeMode.SYSTEM] if no preference is saved.
     */
    val themeFlow: Flow<ThemeMode> = context.dataStore.data.map { preferences ->
        when (preferences[THEME_KEY]) {
            ThemeMode.LIGHT.name -> ThemeMode.LIGHT
            ThemeMode.DARK.name -> ThemeMode.DARK
            else -> ThemeMode.SYSTEM
        }
    }

    /**
     * Persists the selected theme mode to DataStore.
     *
     * @param mode The [ThemeMode] selected by the user (LIGHT, DARK, SYSTEM).
     */
    suspend fun saveTheme(mode: ThemeMode) {
        context.dataStore.edit { preferences ->
            preferences[THEME_KEY] = mode.name
        }
    }

    // ✅ Flow to observe the selected font size (SMALL, MEDIUM, LARGE)
    val fontSizeFlow: Flow<FontSize> = context.dataStore.data.map { preferences ->
        FontSize.valueOf(preferences[FONT_SIZE_KEY] ?: FontSize.MEDIUM.name)
    }

    // ✅ Flow to observe the selected font family (DEFAULT, ALICE)
    val fontFamilyFlow: Flow<FontFamilyChoice> = context.dataStore.data.map { preferences ->
        FontFamilyChoice.valueOf(preferences[FONT_FAMILY_KEY] ?: FontFamilyChoice.DEFAULT.name)
    }

    /**
     * Persists the selected font size to DataStore.
     *
     * @param size The [FontSize] enum chosen by the user.
     */
    suspend fun setFontSize(size: FontSize) {
        context.dataStore.edit { preferences ->
            preferences[FONT_SIZE_KEY] = size.name
        }
    }

    /**
     * Persists the selected font family to DataStore.
     *
     * @param choice The [FontFamilyChoice] enum chosen by the user.
     */
    suspend fun setFontFamily(choice: FontFamilyChoice) {
        context.dataStore.edit { preferences ->
            preferences[FONT_FAMILY_KEY] = choice.name
        }
    }
}
