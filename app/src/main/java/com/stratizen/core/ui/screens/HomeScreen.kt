package com.stratizen.core.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.stratizen.core.data.model.Event
import com.stratizen.core.ui.components.DropdownMenuBox
import com.stratizen.core.ui.components.EventCard
import com.stratizen.core.viewmodel.EventViewModel
import com.stratizen.core.viewmodel.XpViewModel
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*
import com.stratizen.core.ui.components.XpProgressBar

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    navController: NavController,
    viewModel: EventViewModel,
    xpViewModel: XpViewModel
) {
    val xpState by xpViewModel.xp.observeAsState()
    val groupOptions = listOf("General", "Clubs", "Transport", "Class")
    var selectedGroup by remember { mutableStateOf("General") }
    var selectedGroups by remember { mutableStateOf(listOf<String>()) }

    val events by if (selectedGroup == "General") {
        viewModel.allEvents.observeAsState(emptyList())
    } else {
        viewModel.getEventsByGroup(selectedGroup).observeAsState(emptyList())
    }

    var showConfirmDialog by remember { mutableStateOf(false) }
    var eventToDelete by remember { mutableStateOf<Event?>(null) }

    val snackbarHostState = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()

    val groupedEvents = events
        .sortedBy { it.timestamp }
        .groupBy {
            SimpleDateFormat("EEEE, MMM d", Locale.getDefault()).format(Date(it.timestamp))
        }

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = { Text("📅 Stratizen Events") },
                actions = {
                    IconButton(onClick = { navController.navigate("settings") }) {
                        Icon(Icons.Default.Settings, contentDescription = "Settings")
                    }
                }
            )
        },
        snackbarHost = { SnackbarHost(snackbarHostState) },
        floatingActionButton = {
            FloatingActionButton(onClick = { navController.navigate("add_event") }) {
                Icon(Icons.Default.Add, contentDescription = "Add Event")
            }
        }
    ) { paddingValues ->

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            // 🎯 XP Section Below TopAppBar
            xpState?.let { xp ->
                XpProgressBar(
                    points = xp.points,
                    level = xp.level,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp, vertical = 8.dp)
                )
            }

            // 📝 Events Section
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 16.dp, vertical = 8.dp)
            ) {
                // Group Filter
                item {
                    DropdownMenuBox(
                        options = groupOptions,
                        selectedOption = selectedGroup,
                        onOptionSelected = { selectedGroup = it }
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                }

                // Empty State
                if (events.isEmpty()) {
                    item {
                        Text(
                            "No events yet. Tap ➕ to add.",
                            style = MaterialTheme.typography.bodyLarge
                        )
                    }
                } else {
                    groupedEvents.forEach { (date, dayEvents) ->
                        item {
                            Text(
                                text = date,
                                style = MaterialTheme.typography.labelLarge,
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(vertical = 8.dp)
                            )
                        }
                        items(dayEvents) { event ->
                            EventCard(
                                event = event,
                                onDelete = {
                                    eventToDelete = event
                                    showConfirmDialog = true
                                },
                                onEdit = {
                                    navController.navigate("add_event?id=${event.id}")
                                }
                            )
                            Spacer(modifier = Modifier.height(8.dp))
                        }
                    }
                }
            }
        }

        // ⚠️ Delete Confirmation Dialog
        if (showConfirmDialog && eventToDelete != null) {
            AlertDialog(
                onDismissRequest = { showConfirmDialog = false },
                title = { Text("Delete Event?") },
                text = { Text("Are you sure you want to delete \"${eventToDelete?.title}\"?") },
                confirmButton = {
                    TextButton(onClick = {
                        viewModel.delete(eventToDelete!!)
                        scope.launch {
                            val result = snackbarHostState.showSnackbar(
                                message = "Event deleted",
                                actionLabel = "Undo"
                            )
                            if (result == SnackbarResult.ActionPerformed) {
                                viewModel.addEvent(eventToDelete!!)
                            }
                        }
                        showConfirmDialog = false
                    }) {
                        Text("Delete")
                    }
                },
                dismissButton = {
                    TextButton(onClick = { showConfirmDialog = false }) {
                        Text("Cancel")
                    }
                }
            )
        }
    }
}

// Group color mapping utility
@Composable
fun groupColor(group: String): androidx.compose.ui.graphics.Color {
    return when (group) {
        "Clubs" -> MaterialTheme.colorScheme.tertiary
        "Transport" -> MaterialTheme.colorScheme.secondary
        "Class" -> MaterialTheme.colorScheme.primary
        else -> MaterialTheme.colorScheme.surfaceVariant
    }
}
