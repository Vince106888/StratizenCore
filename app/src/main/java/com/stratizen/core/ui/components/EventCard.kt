package com.stratizen.core.ui.components

// Jetpack Compose animation tools
import androidx.compose.animation.*
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccessTime
import androidx.compose.material3.*
import androidx.compose.runtime.*
// Layout + interaction tools
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.unit.dp
// Your app's Event data class
import com.stratizen.core.data.model.Event
// Date formatting
import java.text.SimpleDateFormat
import java.util.*

@Composable
fun EventCard(
    event: Event,              // The event to display
    onDelete: () -> Unit,      // Callback when user wants to delete
    onEdit: () -> Unit         // Callback when user wants to edit
) {
    // State variables to control card behavior
    var expanded by remember { mutableStateOf(false) } // Whether extra details are shown
    var dismissed by remember { mutableStateOf(false) } // Whether this card is removed (hidden)

    // Color based on group
    val groupColor = when (event.group) {
        "General" -> Color(0xFF2196F3)   // Blue
        "School" -> Color(0xFF00BCD4)    // Cyan
        "Clubs" -> Color(0xFF4CAF50)     // Green
        "Class" -> Color(0xFFF44336)     // Red
        "Transport" -> Color(0xFFFF9800) // Orange
        "MyEvents" -> Color(0xFF673AB7)  // Purple
        else -> Color.Gray               // Default
    }

    // Format date for display
    val formattedDate = SimpleDateFormat("EEE, d MMM yyyy • h:mm a", Locale.getDefault())
        .format(Date(event.timestamp))

    // Card UI (if not dismissed)
    if (!dismissed) {
        Card(
            modifier = Modifier
                .fillMaxWidth() // Full width
                .padding(vertical = 6.dp) // Space between cards
                .shadow(2.dp, shape = RoundedCornerShape(16.dp)) // Soft shadow
                .pointerInput(Unit) { // Detect tap and long press gestures
                    detectTapGestures(
                        onLongPress = {
                            dismissed = true // Hide card
                            onDelete()       // Trigger delete callback
                        },
                        onTap = {
                            expanded = !expanded // Toggle extra info visibility
                        }
                    )
                },
            shape = RoundedCornerShape(16.dp),
            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
        ) {

            // Card content layout
            Column {
                // Color bar at the top
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(6.dp)
                        .background(groupColor) // Thin color bar
                )

                // Main event info (title, time, etc.)
                Column(modifier = Modifier.padding(16.dp)) {

                    // Event Title
                    Text(
                        text = event.title,
                        style = MaterialTheme.typography.titleLarge,
                        color = MaterialTheme.colorScheme.primary
                    )

                    Spacer(modifier = Modifier.height(4.dp)) // Space between title and time

                    //Date & time row with icon
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(
                            imageVector = Icons.Default.AccessTime,
                            contentDescription = "Event Time",
                            modifier = Modifier.size(16.dp),
                            tint = MaterialTheme.colorScheme.onSurfaceVariant
                        )

                        Spacer(modifier = Modifier.width(6.dp)) // Space between icon and text

                        Text(
                            text = formattedDate, // Formatted timestamp
                            style = MaterialTheme.typography.bodySmall,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }

                    // Extra details (hidden by default)
                    AnimatedVisibility(
                        visible = expanded,
                        enter = expandVertically() + fadeIn(), // Animation on show
                        exit = shrinkVertically() + fadeOut() // Animation on hide
                    ) {
                        Column {
                            Spacer(modifier = Modifier.height(8.dp))

                            // Event description
                            Text(
                                text = event.description,
                                style = MaterialTheme.typography.bodyMedium,
                                color = MaterialTheme.colorScheme.onSurface
                            )

                            Spacer(modifier = Modifier.height(6.dp)) // Space between details

                            // Group label
                            Text(
                                text = "Group: ${event.group}",
                                style = MaterialTheme.typography.labelMedium,
                                color = groupColor
                            )

                            Spacer(modifier = Modifier.height(10.dp)) // Space between buttons

                            // Edit and Delete buttons
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.End
                            ) {
                                // Edit button
                                TextButton(onClick = onEdit) {
                                    Text("Edit")
                                }

                                // Delete button
                                TextButton(onClick = {
                                    dismissed = true // Hide card
                                    onDelete() // Trigger delete callback
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