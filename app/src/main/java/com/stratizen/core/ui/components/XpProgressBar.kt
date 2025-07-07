package com.stratizen.core.ui.components

import androidx.compose.animation.core.*
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.unit.dp
import kotlin.math.floor

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
    val levelCap = level * 100
    val progressInCurrentLevel = (points % levelCap).coerceIn(0, levelCap)
    val rawProgress = progressInCurrentLevel.toFloat() / levelCap

    // Animate the XP progress bar smoothly
    val animatedProgress by animateFloatAsState(
        targetValue = rawProgress,
        animationSpec = tween(durationMillis = 800, easing = FastOutSlowInEasing),
        label = "XP Progress Animation"
    )

    // Animate scale for level-up pulse when bar hits 100%
    val pulseAnim = remember { Animatable(1f) }

    LaunchedEffect(progressInCurrentLevel == 0 && points > 0) {
        // Trigger pulse when level threshold crossed
        pulseAnim.animateTo(
            targetValue = 1.2f,
            animationSpec = tween(300)
        )
        pulseAnim.animateTo(
            targetValue = 1f,
            animationSpec = tween(300)
        )
    }

    // Dynamic color based on progress
    val progressColor = when {
        rawProgress < 0.4f -> MaterialTheme.colorScheme.primary
        rawProgress < 0.8f -> MaterialTheme.colorScheme.tertiary
        else -> MaterialTheme.colorScheme.secondary
    }

    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
    ) {
        // 🟦 XP Bar
        LinearProgressIndicator(
            progress = animatedProgress,
            modifier = Modifier
                .fillMaxWidth()
                .height(8.dp)
                .scale(pulseAnim.value),
            color = progressColor,
            trackColor = MaterialTheme.colorScheme.surfaceVariant
        )

        Spacer(modifier = Modifier.height(6.dp))

        // 📊 XP Labels
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "Level $level",
                style = MaterialTheme.typography.labelMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
            Text(
                text = "$progressInCurrentLevel / $levelCap XP",
                style = MaterialTheme.typography.labelMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
    }
}
