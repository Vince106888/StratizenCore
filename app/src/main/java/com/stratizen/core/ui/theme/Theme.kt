package com.stratizen.core.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import com.stratizen.core.datastore.FontFamilyChoice
import com.stratizen.core.datastore.FontSize

/**
 * Stratizen App Theme - Applies dynamic color schemes and typography
 * based on system or manual dark mode preference.
 */

// 🎨 Light theme colors
private val LightColors = lightColorScheme(
    primary = Purple40,
    onPrimary = White,
    secondary = PurpleGrey40,
    onSecondary = White,
    background = White,
    onBackground = Black,
    surface = White,
    onSurface = Black
)

// 🌙 Dark theme colors
private val DarkColors = darkColorScheme(
    primary = Purple80,
    onPrimary = Black,
    secondary = PurpleGrey80,
    onSecondary = Black,
    background = Black,
    onBackground = White,
    surface = DarkGray,
    onSurface = White
)

/**
 * Applies the Stratizen design system to the app.
 *
 * @param darkTheme Whether to use the dark theme (defaults to system setting).
 * @param fontSize User-selected font size scale (SMALL, MEDIUM, LARGE).
 * @param fontFamilyChoice User-selected font family (DEFAULT, ALICE).
 * @param content Composable UI to wrap with theme.
 */
@Composable
fun StratizenTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    fontSize: FontSize = FontSize.MEDIUM,
    fontFamilyChoice: FontFamilyChoice = FontFamilyChoice.DEFAULT,
    content: @Composable () -> Unit
) {
    val colorScheme = if (darkTheme) DarkColors else LightColors

    MaterialTheme(
        colorScheme = colorScheme,
        typography = getAppTypography(fontSize, fontFamilyChoice), // ✅ Apply dynamic typography
        shapes = AppShapes,
        content = content
    )
}
