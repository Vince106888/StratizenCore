package com.stratizen.core

import android.app.NotificationChannel
import android.app.NotificationManager
import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.*
import com.stratizen.core.ui.screens.AddEventScreen
import com.stratizen.core.ui.screens.HomeScreen
import com.stratizen.core.ui.theme.StratizenTheme
import com.stratizen.core.viewmodel.EventViewModel
import com.stratizen.core.viewmodel.XpViewModel

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // 🔔 Set up local notification channel
        createNotificationChannel()

        setContent {
            StratizenTheme {
                val navController = rememberNavController()
                val eventViewModel: EventViewModel = viewModel()
                val xpViewModel: XpViewModel = viewModel()

                NavHost(
                    navController = navController,
                    startDestination = "home"
                ) {
                    composable("home") {
                        HomeScreen(
                            navController = navController,
                            viewModel = eventViewModel,
                            xpViewModel = xpViewModel
                        )
                    }
                    composable("add_event") {
                        AddEventScreen(
                            navController = navController,
                            viewModel = eventViewModel,
                            xpViewModel = xpViewModel
                        )
                    }
                }
            }
        }
    }

    // 🔔 Notification channel setup for API 26+
    private fun createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                "event_channel", // Must match ID used in ReminderReceiver
                "Event Reminders",
                NotificationManager.IMPORTANCE_HIGH
            ).apply {
                description = "Stratizen Core event notifications"
            }

            val manager = getSystemService(NotificationManager::class.java)
            manager?.createNotificationChannel(channel)
        }
    }
}
