@file:OptIn(ExperimentalMaterial3Api::class)

package com.stratizen.core.ui.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController

@Composable
fun SettingsDashboardScreen(navController: NavController) {
    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = { Text("Settings") },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Back"
                        )
                    }
                }
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(horizontal = 20.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Spacer(modifier = Modifier.height(12.dp))

            Text(
                text = "Preferences",
                style = MaterialTheme.typography.titleMedium,
                color = MaterialTheme.colorScheme.primary
            )

            SettingsItem(
                icon = Icons.Filled.ColorLens, // 🎨 Theme
                title = "App Theme",
                subtitle = "Light / Dark / System Default",
                onClick = { navController.navigate("theme_settings") }
            )

            SettingsItem(
                icon = Icons.Filled.Notifications, // 🔔 Notifications
                title = "Notifications",
                subtitle = "Manage alerts & reminders",
                onClick = { navController.navigate("notification_settings") }
            )

            SettingsItem(
                icon = Icons.Filled.ManageAccounts, // 👤 Account
                title = "Account",
                subtitle = "Edit profile or logout",
                onClick = { navController.navigate("account_settings") }
            )

            SettingsItem(
                icon = Icons.Filled.Lock, // 🔒 Privacy & Security
                title = "Privacy & Security",
                subtitle = "Control your data visibility",
                onClick = { navController.navigate("privacy_settings") }
            )

            SettingsItem(
                icon = Icons.Filled.Info, // ℹ️ About
                title = "About Stratizen",
                subtitle = "Version 1.0.0",
                onClick = { navController.navigate("about") }
            )
        }
    }
}

@Composable
fun SettingsItem(
    icon: ImageVector,
    title: String,
    subtitle: String,
    onClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() },
        shape = RoundedCornerShape(14.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceVariant
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                imageVector = icon,
                contentDescription = "$title Icon",
                tint = MaterialTheme.colorScheme.primary
            )

            Spacer(modifier = Modifier.width(16.dp))

            Column {
                Text(
                    text = title,
                    style = MaterialTheme.typography.bodyLarge
                )
                Text(
                    text = subtitle,
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        }
    }
}
