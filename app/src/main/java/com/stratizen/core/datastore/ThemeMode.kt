package com.stratizen.core.datastore

/**
 * Enum class that defines the available theme modes for the application UI.
 *
 * Used to control the app’s appearance (light, dark, or system default),
 * typically stored in preferences or datastore and observed in the UI layer.
 */
enum class ThemeMode {

    /**
     * Forces the application to use Light Mode.
     * Useful for users who prefer bright backgrounds and default contrast.
     */
    LIGHT,

    /**
     * Forces the application to use Dark Mode.
     * Reduces eye strain in low-light environments and can save battery on OLED screens.
     */
    DARK,

    /**
     * Follows the system theme (Light or Dark).
     * Reacts automatically to the device's appearance settings.
     */
    SYSTEM
}
