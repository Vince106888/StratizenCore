package com.stratizen.core.ui.theme

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Shapes
import androidx.compose.ui.unit.dp

/**
 * Stratizen App-wide shape theming.
 * Applies consistent corner radius styling for components.
 */
val AppShapes = Shapes(
    extraSmall = RoundedCornerShape(4.dp),   // Chips, small badges
    small = RoundedCornerShape(8.dp),        // Buttons, small cards
    medium = RoundedCornerShape(12.dp),      // Dialogs, inputs
    large = RoundedCornerShape(16.dp),       // Cards, containers
    extraLarge = RoundedCornerShape(28.dp)   // Full-width containers or fancy UI
)
