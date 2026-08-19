package com.example.ui.theme

import android.app.Activity
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.ReadOnlyComposable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalView
import androidx.core.view.WindowCompat

private val LightColorScheme = lightColorScheme(
    primary = PrimaryPink,
    onPrimary = Color.White,
    primaryContainer = SoftPink,
    onPrimaryContainer = PrimaryPinkDark,
    secondary = AccentPurple,
    onSecondary = Color.White,
    secondaryContainer = SoftPurple,
    onSecondaryContainer = AccentPurple,
    background = BackgroundLight,
    onBackground = TextPrimary,
    surface = SurfaceWhite,
    onSurface = TextPrimary,
    surfaceVariant = SoftPink.copy(alpha = 0.5f),
    onSurfaceVariant = TextSecondary,
    outline = BorderSoft
)

private val DarkColorScheme = darkColorScheme(
    primary = PrimaryPinkDarkMode,
    onPrimary = BackgroundDark,
    primaryContainer = SoftPinkDarkMode,
    onPrimaryContainer = PrimaryPinkDarkAccent,
    secondary = AccentPurpleDark,
    onSecondary = BackgroundDark,
    secondaryContainer = SoftPurpleDark,
    onSecondaryContainer = AccentPurpleDark,
    background = BackgroundDark,
    onBackground = TextPrimaryDark,
    surface = SurfaceDark,
    onSurface = TextPrimaryDark,
    surfaceVariant = SurfaceDarkElevated,
    onSurfaceVariant = TextSecondaryDark,
    outline = BorderSoftDark
)

object AppTheme {
    val colors: ExtendedColors
        @Composable
        @ReadOnlyComposable
        get() = LocalExtendedColors.current
}

@Composable
fun MomAndBabyTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val colorScheme = if (darkTheme) DarkColorScheme else LightColorScheme
    val extendedColors = if (darkTheme) DarkExtendedColors else LightExtendedColors
    val view = LocalView.current

    if (!view.isInEditMode && view.context is Activity) {
        val window = (view.context as Activity).window
        SideEffect {
            WindowCompat.getInsetsController(window, view).apply {
                isAppearanceLightStatusBars = !darkTheme
                isAppearanceLightNavigationBars = !darkTheme
            }
        }
    }

    CompositionLocalProvider(
        LocalExtendedColors provides extendedColors
    ) {
        MaterialTheme(
            colorScheme = colorScheme,
            typography = Typography,
            content = content
        )
    }
}

// Backward compatibility alias
@Composable
fun MyApplicationTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    MomAndBabyTheme(darkTheme = darkTheme, content = content)
}
