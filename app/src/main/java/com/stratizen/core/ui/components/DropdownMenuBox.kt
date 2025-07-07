package com.stratizen.core.ui.components

// Imports for layout and clickable behavior
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
// Import for dropdown arrow icon
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
// Material 3 UI components
import androidx.compose.material3.*
// State management (like remember, mutableStateOf)
import androidx.compose.runtime.*
// Modifier to apply styling to UI elements
import androidx.compose.ui.Modifier

/**
 * This is a reusable Composable that shows a single-select dropdown.
 *
 * @param options           A list of strings the user can pick from.
 * @param selectedOption    The current selection shown in the text field.
 * @param onOptionSelected  A callback that runs when a user picks an option.
 */
@Composable
fun DropdownMenuBox(
    options: List<String>,                   // All items the user can select
    selectedOption: String,                  // The item currently selected
    onOptionSelected: (String) -> Unit       // What happens when a user selects something
) {
    // State to control if the dropdown is open or closed
    var expanded by remember { mutableStateOf(false) }

    // A Box is a container used here to stack the dropdown below the text field
    Box(modifier = Modifier.fillMaxWidth()) {

        // The text field showing the selected item
        OutlinedTextField(
            value = selectedOption,            // The text inside the field
            onValueChange = {},                // No typing allowed (read-only dropdown)
            readOnly = true,                   // Prevents user from typing
            label = { Text("Select Group") },  // The label shown above the field

            // The icon at the end of the field
            trailingIcon = {
                IconButton(onClick = { expanded = !expanded }) {
                    Icon(
                        imageVector = Icons.Default.ArrowDropDown, // Downward arrow
                        contentDescription = "Dropdown"            // For accessibility
                    )
                }
            },

            // Modifier to style the field
            modifier = Modifier
                .fillMaxWidth()                // Make it take full width
                .clickable { expanded = true } // Open dropdown when clicked
        )

        // The actual dropdown list
        DropdownMenu(
            expanded = expanded,                // Show or hide the menu
            onDismissRequest = { expanded = false } // Close menu when clicked outside
        ) {
            // Create a menu item for each option
            options.forEach { option ->
                DropdownMenuItem(
                    text = { Text(option) },    // Show the option text
                    onClick = {
                        onOptionSelected(option) // Update selected value
                        expanded = false         // Close the menu
                    }
                )
            }
        }
    }
}
