package com.stratizen.core.ui.components

// Compose animation and UI tools
import androidx.compose.animation.core.*
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.unit.dp

/**
 * An animated and color-changing XP progress bar with level-up feedback.
 *
 * @param points Total XP points (can exceed current level cap).
 * @param level Current level.
 * @param modifier Optional layout modifier.
 */
@Composable
fun XpProgressBar(
    points: Int,
    level: Int,
    modifier: Modifier = Modifier
) {
    // Calculate progress in current level
    val levelCap = level * 100 // XP needed to complete current level
    val progressInCurrentLevel = (points % levelCap).coerceIn(0, levelCap) // XP toward this level
    val rawProgress = progressInCurrentLevel.toFloat() / levelCap // Convert to 0.0 - 1.0 range

    // Animate the XP progress bar smoothly
    val animatedProgress by animateFloatAsState(
        targetValue = rawProgress, // Target progress value
        animationSpec = tween(durationMillis = 800, easing = FastOutSlowInEasing),
        label = "XP Progress Animation"
    )

    // Animate scale for level-up pulse when bar hits 100%
    val pulseAnim = remember { Animatable(1f) } // Default scale is 1x

    LaunchedEffect(progressInCurrentLevel == 0 && points > 0) {
        // Trigger pulse when level threshold crossed
        pulseAnim.animateTo(
            targetValue = 1.2f, // Zoom to 1.2x
            animationSpec = tween(300)
        )
        pulseAnim.animateTo(
            targetValue = 1f, // Return back to normal
            animationSpec = tween(300)
        )
    }

    // Dynamic color based on progress
    val progressColor = when {
        rawProgress < 0.4f -> MaterialTheme.colorScheme.primary // Blue-like color
        rawProgress < 0.8f -> MaterialTheme.colorScheme.tertiary // Yellow/Orange
        else -> MaterialTheme.colorScheme.secondary // Green-like color
    }

    // Layout UI for Progress Bar
    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp) // Space between bars
    ) {
        // XP Progress Bar
        LinearProgressIndicator(
            progress = { animatedProgress }, // Animate progress
            modifier = Modifier
                .fillMaxWidth()
                .height(8.dp)
                .scale(pulseAnim.value), // Pulse on level-up
            color = progressColor, // Color based on progress
            trackColor = MaterialTheme.colorScheme.surfaceVariant, // Background Light gray
        )

        Spacer(modifier = Modifier.height(6.dp)) // Space between bars

        // XP Text Labels
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween, // Space between labels
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "Level $level", // Display level
                style = MaterialTheme.typography.labelMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
            Text(
                text = "$progressInCurrentLevel / $levelCap XP", // Display XP
                style = MaterialTheme.typography.labelMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
    }
}
