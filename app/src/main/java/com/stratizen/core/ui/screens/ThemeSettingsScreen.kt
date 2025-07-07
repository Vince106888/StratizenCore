@file:OptIn(ExperimentalMaterial3Api::class)

package com.stratizen.core.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.stratizen.core.datastore.*
import com.stratizen.core.viewmodel.ThemeViewModel

/**
 * Composable screen for theme and typography settings.
 *
 * Allows the user to:
 * - Choose between Light / Dark / System theme modes
 * - Select font size: Small / Medium / Large
 * - Choose a font family: Default / Alice
 */
@Composable
fun ThemeSettingsScreen(navController: NavController, viewModel: ThemeViewModel) {
    // Collect current theme settings from view model
    val currentTheme = viewModel.themeMode.collectAsState().value
    val currentFontSize = viewModel.fontSize.collectAsState().value
    val currentFontFamily = viewModel.fontFamily.collectAsState().value

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = { Text("App Theme") },
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
                .padding(horizontal = 24.dp, vertical = 16.dp),
            verticalArrangement = Arrangement.spacedBy(28.dp)
        ) {
            // ===============================
            // 🔘 Theme Mode Selection Section
            // ===============================
            Text(
                text = "Choose your preferred theme:",
                style = MaterialTheme.typography.titleMedium,
                color = MaterialTheme.colorScheme.primary
            )

            ThemeMode.values().forEach { mode ->
                val backgroundColor = if (currentTheme == mode)
                    MaterialTheme.colorScheme.primary.copy(alpha = 0.1f)
                else Color.Transparent

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(backgroundColor, shape = RoundedCornerShape(12.dp))
                        .clickable { viewModel.setTheme(mode) }
                        .padding(8.dp), // Padding inside the row
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    RadioButton(
                        selected = currentTheme == mode,
                        onClick = { viewModel.setTheme(mode) }
                    )
                    Spacer(modifier = Modifier.width(6.dp)) // Spacing between radio button and text
                    Column {
                        Text(
                            text = when (mode) {
                                ThemeMode.LIGHT -> "Light"
                                ThemeMode.DARK -> "Dark"
                                ThemeMode.SYSTEM -> "Follow System"
                            },
                            style = MaterialTheme.typography.bodyLarge,
                            color = MaterialTheme.colorScheme.onSurface
                        )
                        Text(
                            text = when (mode) {
                                ThemeMode.LIGHT -> "Bright theme for daylight use"
                                ThemeMode.DARK -> "Dimmed theme for low-light use"
                                ThemeMode.SYSTEM -> "Auto-switches with your device setting"
                            },
                            style = MaterialTheme.typography.bodySmall,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                }
            }

            // ===============================
            // 🔡 Font Size Selection Section
            // ===============================
            Text(
                text = "Font Size",
                style = MaterialTheme.typography.titleMedium,
                color = MaterialTheme.colorScheme.primary
            )

            FontSize.values().forEach { size ->
                val backgroundColor = if (currentFontSize == size)
                    MaterialTheme.colorScheme.primary.copy(alpha = 0.1f)
                else Color.Transparent

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(backgroundColor, RoundedCornerShape(12.dp)) // Rounded corners
                        .clickable { viewModel.updateFontSize(size) }
                        .padding(8.dp), // Padding inside the row
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    RadioButton(
                        selected = currentFontSize == size,
                        onClick = { viewModel.updateFontSize(size) }
                    )
                    Spacer(modifier = Modifier.width(6.dp))
                    Text(
                        text = when (size) {
                            FontSize.SMALL -> "Small"
                            FontSize.MEDIUM -> "Medium"
                            FontSize.LARGE -> "Large"
                        },
                        style = MaterialTheme.typography.bodyLarge
                    )
                }
            }

            // ===============================
            // 🔤 Font Family Selection Section
            // ===============================
            Text(
                text = "Font Family",
                style = MaterialTheme.typography.titleMedium,
                color = MaterialTheme.colorScheme.primary
            )

            listOf(FontFamilyChoice.DEFAULT, FontFamilyChoice.ALICE).forEach { family ->
                val backgroundColor = if (currentFontFamily == family)
                    MaterialTheme.colorScheme.primary.copy(alpha = 0.1f)
                else Color.Transparent

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(backgroundColor, RoundedCornerShape(12.dp))
                        .clickable { viewModel.updateFontFamily(family) }
                        .padding(8.dp), // Padding inside the row
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    RadioButton(
                        selected = currentFontFamily == family,
                        onClick = { viewModel.updateFontFamily(family) }
                    )
                    Spacer(modifier = Modifier.width(6.dp)) // Spacing between radio button and text
                    Text(
                        text = when (family) {
                            FontFamilyChoice.DEFAULT -> "Default"
                            FontFamilyChoice.ALICE -> "Alice"
                        },
                        style = MaterialTheme.typography.bodyLarge
                    )
                }
            }
        }
    }
}
