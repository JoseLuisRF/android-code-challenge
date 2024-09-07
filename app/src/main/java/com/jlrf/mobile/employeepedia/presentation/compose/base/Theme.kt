package com.jlrf.mobile.employeepedia.presentation.compose.base

import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.ui.graphics.Color

val DarkColorScheme = darkColorScheme(
    primary = Purple80,
    secondary = PurpleGrey80,
    tertiary = Pink80
)

val LightColorScheme = lightColorScheme(
    primary = Blue40,
    onPrimary = Color.White,
    tertiary = Pink40,
    background = Gray050,
    onBackground = Color(0xFF1C1B1F),
    primaryContainer = Color.White,
    onPrimaryContainer = Black,
    tertiaryContainer = Blue40,
    surface = Color.White,
)