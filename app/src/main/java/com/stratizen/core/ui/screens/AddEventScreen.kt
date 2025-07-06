@file:OptIn(ExperimentalMaterial3Api::class) // Enables experimental Material3 APIs

package com.stratizen.core.ui.screens

import android.app.*
import android.content.Context
import android.content.Intent
import android.widget.DatePicker
import android.widget.TimePicker
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.stratizen.core.data.model.Event
import com.stratizen.core.notifications.ReminderReceiver
import com.stratizen.core.ui.components.DropdownMenuBox
import com.stratizen.core.viewmodel.EventViewModel
import com.stratizen.core.viewmodel.XpViewModel
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack

@Composable
fun AddEventScreen(
    navController: NavHostController,
    viewModel: EventViewModel,
    xpViewModel: XpViewModel,
    eventId: Int = -1
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

    // Load existing event (edit mode)
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
        Column(modifier = Modifier.padding(padding).padding(16.dp)) {
            // Title input
            OutlinedTextField(
                value = title,
                onValueChange = { title = it },
                label = { Text("Title") },
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(8.dp))

            // Description input
            OutlinedTextField(
                value = description,
                onValueChange = { description = it },
                label = { Text("Description") },
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(8.dp))

            // Group selector
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
                        DatePickerDialog(
                            context,
                            { _: DatePicker, year, month, day ->
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
                        TimePickerDialog(
                            context,
                            { _: TimePicker, hour, minute ->
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

            // Save/Update button
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

                    if (existingEvent != null) {
                        viewModel.update(updatedEvent)
                        scope.launch {
                            snackbarHostState.showSnackbar("Event updated")
                        }
                    } else {
                        viewModel.addEventAndReturnId(updatedEvent) { generatedId ->
                            val finalEvent = updatedEvent.copy(id = generatedId)
                            if (finalEvent.timestamp - 10 * 60 * 1000 > System.currentTimeMillis()) {
                                scheduleNotification(context, finalEvent)
                            }
                            xpViewModel.awardXp(10)
                        }
                        scope.launch {
                            snackbarHostState.showSnackbar("Event saved")
                        }
                    }

                    // Schedule notification if in the future
                    if (updatedEvent.timestamp - 10 * 60 * 1000 > System.currentTimeMillis()) {
                        scheduleNotification(context, updatedEvent)
                    }

                    navController.navigate("home")
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(if (existingEvent != null) "Update Event" else "Save Event")
            }
        }
    }
}

// Notification scheduler
fun scheduleNotification(context: Context, event: Event) {
    val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
    val triggerTime = event.timestamp - 10 * 60 * 1000

    if (triggerTime <= System.currentTimeMillis()) return

    if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.S) {
        if (!alarmManager.canScheduleExactAlarms()) {
            val intent = Intent().apply {
                action = android.provider.Settings.ACTION_REQUEST_SCHEDULE_EXACT_ALARM
                data = android.net.Uri.parse("package:${context.packageName}")
                flags = Intent.FLAG_ACTIVITY_NEW_TASK
            }
            context.startActivity(intent)
            return
        }
    }

    val intent = Intent(context, ReminderReceiver::class.java).apply {
        putExtra("title", event.title)
        putExtra("description", event.description)
    }

    val pendingIntent = PendingIntent.getBroadcast(
        context,
        event.id,
        intent,
        PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
    )

    try {
        alarmManager.setExactAndAllowWhileIdle(
            AlarmManager.RTC_WAKEUP,
            triggerTime,
            pendingIntent
        )
    } catch (e: SecurityException) {
        e.printStackTrace()
    }
}
