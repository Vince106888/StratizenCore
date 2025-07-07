package com.stratizen.core

// ✅ Android core & system components
import android.app.NotificationChannel
import android.app.NotificationManager
import android.os.Build
import android.os.Bundle
import android.Manifest
import android.content.pm.PackageManager
import android.widget.Toast

// ✅ Jetpack libraries for Compose + Activity
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts

// ✅ UI + State + ViewModels
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.Surface
import androidx.core.content.ContextCompat
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel

// ✅ Jetpack Navigation for Compose
import androidx.navigation.compose.rememberNavController

// ✅ Your app-specific imports
import com.stratizen.core.constants.*
import com.stratizen.core.datastore.ThemeMode
import com.stratizen.core.navigation.StratizenNavGraph
import com.stratizen.core.ui.theme.StratizenTheme
import com.stratizen.core.viewmodel.EventViewModel
import com.stratizen.core.viewmodel.ThemeViewModel
import com.stratizen.core.viewmodel.XpViewModel

// 🧠 The entry point of your app
class MainActivity : ComponentActivity() {

    // 🛡️ Permission launcher for notifications (Android 13+)
    private val notificationPermissionLauncher = registerForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { isGranted ->
        // 🎯 Callback if permission is granted/denied
        if (!isGranted) {
            Toast.makeText(this, "Notification permission denied", Toast.LENGTH_SHORT).show()
        }
    }

    // 🚀 Called when the activity is first created
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // 🛠️ Request permissions & setup notifications
        requestNotificationPermission()
        createNotificationChannel()

        // 🧱 Set the app UI content using Jetpack Compose
        setContent {

            // 🎨 ViewModel for theme and typography preferences
            val themeViewModel: ThemeViewModel = viewModel()
            val themeModeState = themeViewModel.themeMode.collectAsStateWithLifecycle()
            val fontSizeState = themeViewModel.fontSize.collectAsStateWithLifecycle()
            val fontFamilyState = themeViewModel.fontFamily.collectAsStateWithLifecycle()

            // 🎚️ Determine current theme
            val themeMode = themeModeState.value ?: ThemeMode.SYSTEM
            val isDarkTheme = when (themeMode) {
                ThemeMode.DARK -> true
                ThemeMode.LIGHT -> false
                ThemeMode.SYSTEM -> isSystemInDarkTheme()
            }

            // 🎨 Apply custom theme with dynamic typography
            StratizenTheme(
                darkTheme = isDarkTheme,
                fontSize = fontSizeState.value,
                fontFamilyChoice = fontFamilyState.value
            ) {
                Surface {
                    // 🧭 Setup navigation controller
                    val navController = rememberNavController()

                    // 📦 Shared view models
                    val eventViewModel: EventViewModel = viewModel()
                    val xpViewModel: XpViewModel = viewModel()

                    // 🗺️ Define navigation routes
                    StratizenNavGraph(
                        navController = navController,
                        eventViewModel = eventViewModel,
                        themeViewModel = themeViewModel,
                        xpViewModel = xpViewModel
                    )

                }
            }
        }
    }

    /**
     * 🚨 Create a notification channel required by Android 8.0+ to show notifications.
     */
    private fun createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                NOTIFICATION_CHANNEL_ID,     // 🔑 Unique channel ID
                NOTIFICATION_CHANNEL_NAME,   // 🏷️ User-visible name
                NotificationManager.IMPORTANCE_HIGH // 🔔 Importance level
            ).apply {
                description = NOTIFICATION_CHANNEL_DESC // 📝 Description
            }

            val manager = getSystemService(NotificationManager::class.java)
            manager?.createNotificationChannel(channel)
        }
    }

    /**
     * 🚀 Requests runtime notification permission (Android 13+ only).
     */
    private fun requestNotificationPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            val permissionCheck = ContextCompat.checkSelfPermission(
                this,
                Manifest.permission.POST_NOTIFICATIONS
            )
            if (permissionCheck != PackageManager.PERMISSION_GRANTED) {
                notificationPermissionLauncher.launch(Manifest.permission.POST_NOTIFICATIONS)
            }
        }
    }
}
