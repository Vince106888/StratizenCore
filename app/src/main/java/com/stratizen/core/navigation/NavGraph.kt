// 📁 com/stratizen/core/navigation/NavGraph.kt

package com.stratizen.core.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.stratizen.core.ui.screens.*
import com.stratizen.core.viewmodel.EventViewModel
import com.stratizen.core.viewmodel.ThemeViewModel
import com.stratizen.core.viewmodel.XpViewModel

@Composable
fun StratizenNavGraph(
    navController: NavHostController,
    eventViewModel: EventViewModel,
    themeViewModel: ThemeViewModel,
    xpViewModel: XpViewModel
) {
    NavHost(
        navController = navController,
        startDestination = "home"
    ) {
        // 🏠 Home
        composable("home") {
            HomeScreen(
                navController = navController,
                viewModel = eventViewModel,
                xpViewModel = xpViewModel
            )
        }

        // ➕ Add Event
        composable("add_event") {
            AddEventScreen(
                navController = navController,
                viewModel = eventViewModel,
                xpViewModel = xpViewModel
            )
        }

        // ⚙️ Settings Dashboard
        composable("settings") {
            SettingsDashboardScreen(navController)
        }

        // 🎨 Theme Settings
        composable("theme_settings") {
            ThemeSettingsScreen(
                navController = navController,
                viewModel = themeViewModel
            )
        }

        // 🔔 Notification Settings
        composable("notification_settings") {
            NotificationSettingsScreen(navController)
        }

        // 👤 Account Settings
        composable("account_settings") {
            AccountSettingsScreen(navController)
        }

        // 🔒 Privacy Settings
        composable("privacy_settings") {
            PrivacySettingsScreen(navController)
        }

        // ℹ️ About
        composable("about") {
            AboutScreen(navController)
        }
    }
}
