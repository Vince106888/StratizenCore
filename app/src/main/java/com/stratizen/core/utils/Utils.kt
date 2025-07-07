package com.stratizen.core.utils

import androidx.compose.ui.graphics.Color
import java.text.SimpleDateFormat
import java.util.*

/**
 * Formats a given timestamp into a readable string:
 * - "Today" if the date is today
 * - "Tomorrow" if it's the next day
 * - Else formatted as "Monday, Jul 8"
 */
fun formatDate(timestamp: Long): String {
    val today = Calendar.getInstance()
    val eventDate = Calendar.getInstance().apply { timeInMillis = timestamp }

    return when {
        isSameDay(today, eventDate) -> "Today"
        isTomorrow(today, eventDate) -> "Tomorrow"
        else -> {
            val formatter = SimpleDateFormat("EEEE, MMM d", Locale.getDefault())
            formatter.format(Date(timestamp))
        }
    }
}

/**
 * Checks if two calendar dates fall on the same day.
 */
private fun isSameDay(cal1: Calendar, cal2: Calendar): Boolean =
    cal1.get(Calendar.YEAR) == cal2.get(Calendar.YEAR) &&
            cal1.get(Calendar.DAY_OF_YEAR) == cal2.get(Calendar.DAY_OF_YEAR)

/**
 * Checks if the second calendar represents tomorrow.
 */
private fun isTomorrow(today: Calendar, other: Calendar): Boolean {
    return Calendar.getInstance().apply { timeInMillis = today.timeInMillis }.run {
        add(Calendar.DAY_OF_YEAR, 1)
        isSameDay(this, other)
    }
}

/**
 * Maps a group name to a color for display use.
 */
fun groupColor(group: String): Color = when (group.lowercase(Locale.ROOT)) {
    "clubs"     -> Color(0xFF4DB6AC) // Teal
    "transport" -> Color(0xFF64B5F6) // Light Blue
    "class"     -> Color(0xFFFFB74D) // Orange
    else        -> Color(0xFF9575CD) // Purple (default)
}

/**
 * Returns a greeting based on the current time of day.
 */
fun greeting(): String {
    return when (Calendar.getInstance().get(Calendar.HOUR_OF_DAY)) {
        in 0..11 -> "Good morning"
        in 12..16 -> "Good afternoon"
        else -> "Good evening"
    }
}
