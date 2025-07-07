// Opt-in to experimental Material 3 APIs
@file:OptIn(ExperimentalMaterial3Api::class)

// Package where this screen lives
package com.stratizen.core.ui.screens

// Android system components for date/time pickers
import android.app.DatePickerDialog
import android.app.TimePickerDialog
// Compose UI tools
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
// Back icon
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
// Material 3 components like TextField, Scaffold, TopAppBar, etc.
import androidx.compose.material3.*
// Compose state management
import androidx.compose.runtime.*
// Observe LiveData from ViewModel
import androidx.compose.runtime.livedata.observeAsState
// Modifier for layout behavior
import androidx.compose.ui.Modifier
// Context for dialogs
import androidx.compose.ui.platform.LocalContext
// Spacing units
import androidx.compose.ui.unit.dp
// Navigation controller for screen routing
import androidx.navigation.NavHostController
// Data model for events
import com.stratizen.core.data.model.Event
// Custom dropdown component
import com.stratizen.core.ui.components.DropdownMenuBox
// ViewModels for event data and XP system
import com.stratizen.core.viewmodel.EventViewModel
import com.stratizen.core.viewmodel.XpViewModel
// Coroutine support
import kotlinx.coroutines.launch
// Date formatting utilities
import java.text.SimpleDateFormat
import java.util.*

/**
 * Screen for creating or editing an event.
 */
@Composable
fun AddEventScreen(
    navController: NavHostController,   // Used to navigate between screens
    viewModel: EventViewModel,          // Manages events
    xpViewModel: XpViewModel,           // Manages XP rewards
    eventId: Int = -1                   // Default = -1 (means new event)
) {
    val context = LocalContext.current  // Current Android context (needed for dialog)
    val calendar = remember { Calendar.getInstance() } // Current date/time reference

    // State variables to hold form data
    var title by remember { mutableStateOf("") }
    var description by remember { mutableStateOf("") }
    var selectedGroup by remember { mutableStateOf("General") }
    var date by remember { mutableLongStateOf(calendar.timeInMillis) } // Date/time in millis

    // Snackbar to show feedback messages
    val snackbarHostState = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope() // Needed for launching suspend functions

    // Load event from DB if we're editing an existing one
    val existingEvent by viewModel.getEventById(eventId).observeAsState()

    // If we're editing, populate the form with existing values
    LaunchedEffect(existingEvent) {
        existingEvent?.let {
            title = it.title
            description = it.description
            selectedGroup = it.group
            date = it.timestamp
        }
    }

    // Group dropdown choices
    val groupOptions = listOf("General", "School", "Clubs", "Class", "Transport", "MyEvents")

    // Formatters for date and time
    val dateFormat = SimpleDateFormat("EEE, d MMM yyyy", Locale.getDefault())
    val timeFormat = SimpleDateFormat("h:mm a", Locale.getDefault())

    // Layout structure for screen
    Scaffold(
        topBar = {
            // Title bar
            CenterAlignedTopAppBar(
                title = {
                    Text(if (existingEvent != null) "Edit Event" else "Add Event")
                },
                navigationIcon = {
                    // Back button
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
                    }
                }
            )
        },
        snackbarHost = { SnackbarHost(snackbarHostState) } // To show feedback messages
    ) { padding ->

        // Main content column
        Column(
            modifier = Modifier
                .padding(padding)       // Adjusts for top bar
                .padding(16.dp)         // Internal screen padding
        ) {

            // Title input
            OutlinedTextField(
                value = title,
                onValueChange = { title = it },
                label = { Text("Title") },
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(8.dp)) // Space between fields

            // Description input
            OutlinedTextField(
                value = description,
                onValueChange = { description = it },
                label = { Text("Description") },
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(8.dp))

            // Group selection
            DropdownMenuBox(
                options = groupOptions,
                selectedOption = selectedGroup,
                onOptionSelected = { selectedGroup = it }
            )

            Spacer(modifier = Modifier.height(8.dp))

            // Date picker
            Text(
                "Date: ${dateFormat.format(Date(date))}",
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable {
                        // Show date picker dialog
                        DatePickerDialog(
                            context,
                            { _, year, month, day ->
                                calendar.set(Calendar.YEAR, year)
                                calendar.set(Calendar.MONTH, month)
                                calendar.set(Calendar.DAY_OF_MONTH, day)
                                date = calendar.timeInMillis
                            },
                            calendar.get(Calendar.YEAR),
                            calendar.get(Calendar.MONTH),
                            calendar.get(Calendar.DAY_OF_MONTH)
                        ).show()
                    }
            )

            Spacer(modifier = Modifier.height(4.dp))

            // Time picker
            Text(
                "Time: ${timeFormat.format(Date(date))}",
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable {
                        // Show time picker dialog
                        TimePickerDialog(
                            context,
                            { _, hour, minute ->
                                calendar.set(Calendar.HOUR_OF_DAY, hour)
                                calendar.set(Calendar.MINUTE, minute)
                                date = calendar.timeInMillis
                            },
                            calendar.get(Calendar.HOUR_OF_DAY),
                            calendar.get(Calendar.MINUTE),
                            false // 12-hour format
                        ).show()
                    }
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Save/Update button
            Button(
                onClick = {
                    // Validation: must have a title
                    if (title.isBlank()) {
                        scope.launch {
                            snackbarHostState.showSnackbar("Please enter a title")
                        }
                        return@Button
                    }

                    // Create event object
                    val updatedEvent = Event(
                        id = existingEvent?.id ?: 0,
                        title = title,
                        description = description,
                        group = selectedGroup,
                        timestamp = date
                    )

                    // Save or update the event
                    scope.launch {
                        try {
                            if (existingEvent != null) {
                                viewModel.update(updatedEvent)
                                snackbarHostState.showSnackbar("Event updated")
                            } else {
                                viewModel.addEvent(updatedEvent)
                                xpViewModel.awardXp(10) // Award XP
                                snackbarHostState.showSnackbar("Event saved")
                            }

                            navController.navigate("home") // Go back to home
                        } catch (e: Exception) {
                            snackbarHostState.showSnackbar("Error: ${e.message}")
                        }
                    }
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                // Button label changes based on mode
                Text(if (existingEvent != null) "Update Event" else "Save Event")
            }
        }
    }
}

