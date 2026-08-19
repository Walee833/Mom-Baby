package com.example.ui.theme

import androidx.compose.runtime.Immutable
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.graphics.Color

// Light Palette
val PrimaryPink = Color(0xFFE83E8C)
val PrimaryPinkDark = Color(0xFFC2185B)
val PrimaryPinkLight = Color(0xFFF8BBD0)
val SoftPink = Color(0xFFFCE4EC)
val BackgroundLight = Color(0xFFFFF7FA)
val SurfaceWhite = Color(0xFFFFFFFF)

// Dark Palette - warm deep velvet noir with blush/lavender luminous accents
val PrimaryPinkDarkMode = Color(0xFFFF65A3)
val PrimaryPinkDarkAccent = Color(0xFFFF80B7)
val SoftPinkDarkMode = Color(0xFF381827)
val BackgroundDark = Color(0xFF120E15)
val SurfaceDark = Color(0xFF1C1622)
val SurfaceDarkElevated = Color(0xFF261E2E)

// Accent & Category Tones - Light
val AccentPurple = Color(0xFF8E24AA)
val SoftPurple = Color(0xFFF3E5F5)
val SuccessGreen = Color(0xFF2E7D32)
val LightGreen = Color(0xFFE8F5E9)
val InfoBlue = Color(0xFF1976D2)
val LightBlue = Color(0xFFE3F2FD)
val WarningOrange = Color(0xFFF57C00)
val LightOrange = Color(0xFFFFF3E0)
val EmergencyRed = Color(0xFFD32F2F)
val LightRed = Color(0xFFFFEBEE)

// Accent & Category Tones - Dark
val AccentPurpleDark = Color(0xFFBA68C8)
val SoftPurpleDark = Color(0xFF2F1D38)
val SuccessGreenDark = Color(0xFF81C784)
val LightGreenDark = Color(0xFF142B1A)
val InfoBlueDark = Color(0xFF64B5F6)
val LightBlueDark = Color(0xFF13273E)
val WarningOrangeDark = Color(0xFFFFB74D)
val LightOrangeDark = Color(0xFF3D2713)
val EmergencyRedDark = Color(0xFFE57373)
val LightRedDark = Color(0xFF3B1519)

// Neutrals - Light
val TextPrimary = Color(0xFF212121)
val TextSecondary = Color(0xFF757575)
val TextTertiary = Color(0xFFBDBDBD)
val BorderSoft = Color(0xFFF1D4E0)
val CardBorder = Color(0xFFF8D7E3)

// Neutrals - Dark
val TextPrimaryDark = Color(0xFFFCEBF3)
val TextSecondaryDark = Color(0xFFBBA7B6)
val TextTertiaryDark = Color(0xFF7E6E7B)
val BorderSoftDark = Color(0xFF42283A)
val CardBorderDark = Color(0xFF362030)

@Immutable
data class ExtendedColors(
    val primary: Color,
    val primaryVariant: Color,
    val softPink: Color,
    val background: Color,
    val surface: Color,
    val surfaceElevated: Color,
    val accentPurple: Color,
    val softPurple: Color,
    val successGreen: Color,
    val lightGreen: Color,
    val infoBlue: Color,
    val lightBlue: Color,
    val warningOrange: Color,
    val lightOrange: Color,
    val emergencyRed: Color,
    val lightRed: Color,
    val textPrimary: Color,
    val textSecondary: Color,
    val textTertiary: Color,
    val borderSoft: Color,
    val cardBorder: Color,
    val isDark: Boolean
)

val LightExtendedColors = ExtendedColors(
    primary = PrimaryPink,
    primaryVariant = PrimaryPinkDark,
    softPink = SoftPink,
    background = BackgroundLight,
    surface = SurfaceWhite,
    surfaceElevated = Color(0xFFFAF2F6),
    accentPurple = AccentPurple,
    softPurple = SoftPurple,
    successGreen = SuccessGreen,
    lightGreen = LightGreen,
    infoBlue = InfoBlue,
    lightBlue = LightBlue,
    warningOrange = WarningOrange,
    lightOrange = LightOrange,
    emergencyRed = EmergencyRed,
    lightRed = LightRed,
    textPrimary = TextPrimary,
    textSecondary = TextSecondary,
    textTertiary = TextTertiary,
    borderSoft = BorderSoft,
    cardBorder = CardBorder,
    isDark = false
)

val DarkExtendedColors = ExtendedColors(
    primary = PrimaryPinkDarkMode,
    primaryVariant = PrimaryPinkDarkAccent,
    softPink = SoftPinkDarkMode,
    background = BackgroundDark,
    surface = SurfaceDark,
    surfaceElevated = SurfaceDarkElevated,
    accentPurple = AccentPurpleDark,
    softPurple = SoftPurpleDark,
    successGreen = SuccessGreenDark,
    lightGreen = LightGreenDark,
    infoBlue = InfoBlueDark,
    lightBlue = LightBlueDark,
    warningOrange = WarningOrangeDark,
    lightOrange = LightOrangeDark,
    emergencyRed = EmergencyRedDark,
    lightRed = LightRedDark,
    textPrimary = TextPrimaryDark,
    textSecondary = TextSecondaryDark,
    textTertiary = TextTertiaryDark,
    borderSoft = BorderSoftDark,
    cardBorder = CardBorderDark,
    isDark = true
)

val LocalExtendedColors = staticCompositionLocalOf { LightExtendedColors }
