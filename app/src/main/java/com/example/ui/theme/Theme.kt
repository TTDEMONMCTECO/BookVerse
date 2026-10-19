package com.example.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import com.example.data.local.AppThemeMode

private val DarkColorScheme = darkColorScheme(
    primary = BookPrimary,
    onPrimary = Color.White,
    primaryContainer = BookPrimaryContainer,
    onPrimaryContainer = BookOnPrimaryContainer,
    secondary = BookSecondary,
    onSecondary = Color.Black,
    secondaryContainer = BookSecondaryContainer,
    onSecondaryContainer = BookOnSecondaryContainer,
    tertiary = BookTertiary,
    onTertiary = Color.White,
    tertiaryContainer = BookTertiaryContainer,
    background = BookDarkBackground,
    onBackground = BookTextPrimary,
    surface = BookDarkSurface,
    onSurface = BookTextPrimary,
    surfaceVariant = BookDarkSurfaceVariant,
    onSurfaceVariant = BookTextSecondary,
    outline = BookDarkCardBorder
)

private val LightColorScheme = lightColorScheme(
    primary = BookLightPrimary,
    onPrimary = Color.White,
    primaryContainer = BookLightPrimaryContainer,
    onPrimaryContainer = BookLightOnPrimaryContainer,
    secondary = BookLightSecondary,
    onSecondary = Color.White,
    secondaryContainer = Color(0xFFFFE0A3),
    onSecondaryContainer = Color(0xFF281800),
    tertiary = Color(0xFFE02E54),
    onTertiary = Color.White,
    tertiaryContainer = Color(0xFFFFD9DF),
    background = BookLightBackground,
    onBackground = BookLightTextPrimary,
    surface = BookLightSurface,
    onSurface = BookLightTextPrimary,
    surfaceVariant = BookLightSurfaceVariant,
    onSurfaceVariant = BookLightTextSecondary,
    outline = BookLightCardBorder
)

@Composable
fun MyApplicationTheme(
    themeMode: AppThemeMode = AppThemeMode.DARK,
    content: @Composable () -> Unit
) {
    val isDark = when (themeMode) {
        AppThemeMode.SYSTEM -> isSystemInDarkTheme()
        AppThemeMode.DARK -> true
        AppThemeMode.LIGHT -> false
    }

    val colorScheme = if (isDark) DarkColorScheme else LightColorScheme

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}

@Composable
fun MyApplicationTheme(
    darkTheme: Boolean,
    content: @Composable () -> Unit
) {
    val colorScheme = if (darkTheme) DarkColorScheme else LightColorScheme

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}
