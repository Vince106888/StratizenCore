package com.stratizen.core.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.stratizen.core.datastore.ThemeMode
import com.stratizen.core.viewmodel.ThemeViewModel

@Composable
fun ThemeSettingsScreen(navController: NavController, viewModel: ThemeViewModel) {
    val currentTheme = viewModel.themeMode.collectAsState().value

    Column(modifier = Modifier
        .fillMaxSize()
        .padding(24.dp)) {

        Text(
            text = "Choose App Theme",
            style = MaterialTheme.typography.titleLarge,
            modifier = Modifier.padding(bottom = 16.dp)
        )

        ThemeMode.values().forEach { mode ->
            Row(
                verticalAlignment = androidx.compose.ui.Alignment.CenterVertically,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp)
            ) {
                RadioButton(
                    selected = currentTheme == mode,
                    onClick = { viewModel.setTheme(mode) }
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = when (mode) {
                        ThemeMode.LIGHT -> "Light"
                        ThemeMode.DARK -> "Dark"
                        ThemeMode.SYSTEM -> "Follow System"
                    },
                    style = MaterialTheme.typography.bodyLarge
                )
            }
        }
    }
}
