package com.stratizen.core

import android.app.NotificationChannel
import android.app.NotificationManager
import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.Surface
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.stratizen.core.datastore.ThemeMode
import com.stratizen.core.ui.screens.*
import com.stratizen.core.ui.theme.StratizenTheme
import com.stratizen.core.viewmodel.EventViewModel
import com.stratizen.core.viewmodel.ThemeViewModel
import com.stratizen.core.viewmodel.XpViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        createNotificationChannel()

        setContent {
            val themeViewModel: ThemeViewModel = viewModel()
            val themeModeState = themeViewModel.themeMode.collectAsStateWithLifecycle()
            val themeMode: ThemeMode = themeModeState.value ?: ThemeMode.SYSTEM

            val isDarkTheme = when (themeMode) {
                ThemeMode.DARK -> true
                ThemeMode.LIGHT -> false
                ThemeMode.SYSTEM -> isSystemInDarkTheme()
            }

            StratizenTheme(darkTheme = isDarkTheme) {
                Surface {
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
                        composable("settings") {
                            SettingsDashboardScreen(navController)
                        }
                        composable("theme_settings") {
                            ThemeSettingsScreen(
                                navController = navController,
                                viewModel = themeViewModel
                            )
                        }
                        composable("notification_settings") {
                            NotificationSettingsScreen(navController)
                        }
                        composable("account_settings") {
                            AccountSettingsScreen(navController)
                        }
                        composable("privacy_settings") {
                            PrivacySettingsScreen(navController)
                        }
                        composable("about") {
                            AboutScreen(navController)
                        }
                    }
                }
            }
        }
    }

    private fun createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                "event_channel",
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
