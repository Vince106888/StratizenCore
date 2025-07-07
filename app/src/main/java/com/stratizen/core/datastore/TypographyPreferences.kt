package com.stratizen.core.datastore

enum class FontSize { SMALL, MEDIUM, LARGE }

enum class FontFamilyChoice { DEFAULT, ALICE }

data class TypographyPreferences(
    val fontSize: FontSize = FontSize.MEDIUM,
    val fontFamily: FontFamilyChoice = FontFamilyChoice.DEFAULT
)
