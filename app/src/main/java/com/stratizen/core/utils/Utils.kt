package com.stratizen.core.utils

import androidx.compose.ui.graphics.Color
import java.text.SimpleDateFormat
import java.util.*

fun formatDate(timestamp: Long): String {
    val today = Calendar.getInstance()
    val eventDate = Calendar.getInstance().apply { timeInMillis = timestamp }

    return when {
        isSameDay(today, eventDate) -> "Today"
        isTomorrow(today, eventDate) -> "Tomorrow"
        else -> {
            val sdf = SimpleDateFormat("EEEE, MMM d", Locale.getDefault())
            sdf.format(Date(timestamp))
        }
    }
}

private fun isSameDay(cal1: Calendar, cal2: Calendar): Boolean =
    cal1.get(Calendar.YEAR) == cal2.get(Calendar.YEAR) &&
            cal1.get(Calendar.DAY_OF_YEAR) == cal2.get(Calendar.DAY_OF_YEAR)

private fun isTomorrow(cal1: Calendar, cal2: Calendar): Boolean =
    (cal1.clone() as Calendar).apply { add(Calendar.DAY_OF_YEAR, 1) }.let {
    isSameDay(it as Calendar, cal2)
    }

fun groupColor(group: String): Color = when (group.lowercase()) {
    "clubs" -> Color(0xFF81D4FA)
    "transport" -> Color(0xFFA5D6A7)
    "class" -> Color(0xFFFFCC80)
    else -> Color(0xFFB39DDB)
}

fun greeting(): String {
    val hour = Calendar.getInstance().get(Calendar.HOUR_OF_DAY)
    return when (hour) {
        in 0..11 -> "Good morning"
        in 12..16 -> "Good afternoon"
        else -> "Good evening"
    }
}
