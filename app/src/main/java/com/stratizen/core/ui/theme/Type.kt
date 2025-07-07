package com.stratizen.core.ui.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.*
import androidx.compose.ui.unit.sp
import com.stratizen.core.R
import com.stratizen.core.datastore.FontSize
import com.stratizen.core.datastore.FontFamilyChoice

// 🎨 Define custom font family: Alice
val AliceFontFamily = FontFamily(
    Font(R.font.alice_regular, weight = FontWeight.Normal)
)

/**
 * Builds a dynamic Typography based on user preferences.
 *
 * @param fontSize The preferred font size scale (SMALL, MEDIUM, LARGE).
 * @param fontFamilyChoice The selected font family (DEFAULT, ALICE).
 * @return A [Typography] instance customized to user settings.
 */
fun getAppTypography(fontSize: FontSize, fontFamilyChoice: FontFamilyChoice): Typography {
    // 🧮 Scale factor based on selected size
    val scale = when (fontSize) {
        FontSize.SMALL -> 0.85f
        FontSize.MEDIUM -> 1f
        FontSize.LARGE -> 1.15f
    }

    // 🔠 Choose font family
    val fontFamily = when (fontFamilyChoice) {
        FontFamilyChoice.DEFAULT -> FontFamily.Default
        FontFamilyChoice.ALICE -> AliceFontFamily
    }

    return Typography(
        displayLarge = TextStyle(
            fontFamily = fontFamily,
            fontWeight = FontWeight.Normal,
            fontSize = (36.sp * scale)
        ),
        titleLarge = TextStyle(
            fontFamily = fontFamily,
            fontWeight = FontWeight.Normal,
            fontSize = (22.sp * scale)
        ),
        bodyLarge = TextStyle(
            fontFamily = fontFamily,
            fontWeight = FontWeight.Normal,
            fontSize = (16.sp * scale)
        ),
        labelMedium = TextStyle(
            fontFamily = fontFamily,
            fontWeight = FontWeight.Normal,
            fontSize = (12.sp * scale)
        )
        // 🔧 Add more styles if needed
    )
}
