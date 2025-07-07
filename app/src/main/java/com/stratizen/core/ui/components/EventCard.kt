package com.stratizen.core.ui.components

import androidx.compose.animation.*
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccessTime
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.unit.dp
import com.stratizen.core.data.model.Event
import kotlinx.coroutines.delay
import java.text.SimpleDateFormat
import java.util.*

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun EventCard(
    event: Event,
    color: Color = MaterialTheme.colorScheme.primaryContainer,
    onDelete: () -> Unit,
    onEdit: () -> Unit
) {
    // ───────────────────────────────
    // Visual state
    var expanded by remember { mutableStateOf(false) }
    var dismissed by remember { mutableStateOf(false) }

    // ───────────────────────────────
    // Group color tag for flair
    val groupColor = when (event.group) {
        "General" -> Color(0xFF2196F3)
        "Clubs" -> Color(0xFF4CAF50)
        "Transport" -> Color(0xFFFF9800)
        "Class" -> Color(0xFFF44336)
        else -> Color.Gray
    }

    val formattedDate = SimpleDateFormat("EEE, d MMM yyyy • h:mm a", Locale.getDefault())
        .format(Date(event.timestamp))

    // ───────────────────────────────
    // Swipe-to-dismiss animation (basic)
    if (!dismissed) {
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 6.dp)
                .shadow(2.dp, shape = RoundedCornerShape(16.dp))
                .pointerInput(Unit) {
                    detectTapGestures(
                        onLongPress = {
                            dismissed = true
                            // Optional delay before deletion
                            // e.g., show Undo Snackbar here
                            onDelete()
                        },
                        onTap = {
                            expanded = !expanded
                        }
                    )
                },
            shape = RoundedCornerShape(16.dp),
            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
        ) {
            Column {
                // Group color strip
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(6.dp)
                        .background(groupColor)
                )

                // Header: Title + Time
                Column(modifier = Modifier.padding(16.dp)) {
                    Text(
                        text = event.title,
                        style = MaterialTheme.typography.titleLarge,
                        color = MaterialTheme.colorScheme.primary
                    )

                    Spacer(modifier = Modifier.height(4.dp))

                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(
                            imageVector = Icons.Default.AccessTime,
                            contentDescription = "Event Time",
                            modifier = Modifier.size(16.dp),
                            tint = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                        Spacer(modifier = Modifier.width(6.dp))
                        Text(
                            text = formattedDate,
                            style = MaterialTheme.typography.bodySmall,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }

                    // Expandable content
                    AnimatedVisibility(
                        visible = expanded,
                        enter = expandVertically() + fadeIn(),
                        exit = shrinkVertically() + fadeOut()
                    ) {
                        Column {
                            Spacer(modifier = Modifier.height(8.dp))

                            Text(
                                text = event.description,
                                style = MaterialTheme.typography.bodyMedium,
                                color = MaterialTheme.colorScheme.onSurface
                            )

                            Spacer(modifier = Modifier.height(6.dp))

                            Text(
                                text = "Group: ${event.group}",
                                style = MaterialTheme.typography.labelMedium,
                                color = groupColor
                            )

                            Spacer(modifier = Modifier.height(10.dp))

                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.End
                            ) {
                                TextButton(onClick = onEdit) {
                                    Text("Edit")
                                }
                                TextButton(onClick = {
                                    dismissed = true
                                    onDelete()
                                }) {
                                    Text("Delete")
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}
