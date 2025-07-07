@file:OptIn(ExperimentalMaterial3Api::class)

package com.stratizen.core.ui.screens

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.stratizen.core.data.model.Event
import com.stratizen.core.ui.components.DropdownMenuBox
import com.stratizen.core.viewmodel.EventViewModel
import com.stratizen.core.viewmodel.XpViewModel
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*

@Composable
fun AddEventScreen(
    navController: NavHostController,
    viewModel: EventViewModel,
    xpViewModel: XpViewModel,
    eventId: Int = -1 // -1 indicates a new event
) {
    val context = LocalContext.current
    val calendar = remember { Calendar.getInstance() }

    var title by remember { mutableStateOf("") }
    var description by remember { mutableStateOf("") }
    var selectedGroup by remember { mutableStateOf("General") }
    var date by remember { mutableLongStateOf(calendar.timeInMillis) }

    val snackbarHostState = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()

    val existingEvent by viewModel.getEventById(eventId).observeAsState()

    LaunchedEffect(existingEvent) {
        existingEvent?.let {
            title = it.title
            description = it.description
            selectedGroup = it.group
            date = it.timestamp
        }
    }

    val groupOptions = listOf("General", "Clubs", "Transport", "Class")
    val dateFormat = SimpleDateFormat("EEE, d MMM yyyy", Locale.getDefault())
    val timeFormat = SimpleDateFormat("h:mm a", Locale.getDefault())

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text(if (existingEvent != null) "Edit Event" else "Add Event")
                },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Back")
                    }
                }
            )
        },
        snackbarHost = { SnackbarHost(snackbarHostState) }
    ) { padding ->
        Column(
            modifier = Modifier
                .padding(padding)
                .padding(16.dp)
        ) {
            OutlinedTextField(
                value = title,
                onValueChange = { title = it },
                label = { Text("Title") },
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(8.dp))

            OutlinedTextField(
                value = description,
                onValueChange = { description = it },
                label = { Text("Description") },
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(8.dp))

            DropdownMenuBox(
                options = groupOptions,
                selectedOption = selectedGroup,
                onOptionSelected = { selectedGroup = it }
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                "Date: ${dateFormat.format(Date(date))}",
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable {
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

            Text(
                "Time: ${timeFormat.format(Date(date))}",
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable {
                        TimePickerDialog(
                            context,
                            { _, hour, minute ->
                                calendar.set(Calendar.HOUR_OF_DAY, hour)
                                calendar.set(Calendar.MINUTE, minute)
                                date = calendar.timeInMillis
                            },
                            calendar.get(Calendar.HOUR_OF_DAY),
                            calendar.get(Calendar.MINUTE),
                            false
                        ).show()
                    }
            )

            Spacer(modifier = Modifier.height(16.dp))

            Button(
                onClick = {
                    if (title.isBlank()) {
                        scope.launch {
                            snackbarHostState.showSnackbar("Please enter a title")
                        }
                        return@Button
                    }

                    val updatedEvent = Event(
                        id = existingEvent?.id ?: 0,
                        title = title,
                        description = description,
                        group = selectedGroup,
                        timestamp = date
                    )

                    scope.launch {
                        try {
                            if (existingEvent != null) {
                                viewModel.update(updatedEvent)
                                snackbarHostState.showSnackbar("Event updated")
                            } else {
                                viewModel.addEvent(updatedEvent)
                                xpViewModel.awardXp(10)
                                snackbarHostState.showSnackbar("Event saved")
                            }
                            navController.navigate("home")
                        } catch (e: Exception) {
                            snackbarHostState.showSnackbar("Error: ${e.message}")
                        }
                    }
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(if (existingEvent != null) "Update Event" else "Save Event")
            }
        }
    }
}
