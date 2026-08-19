package com.example.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.model.UserProfile
import com.example.ui.theme.*
import com.example.ui.viewmodel.Screen
import com.example.ui.viewmodel.ThemeMode

@Composable
fun SideDrawerContent(
    userProfile: UserProfile,
    currentScreen: Screen,
    themeMode: ThemeMode = ThemeMode.SYSTEM,
    onToggleTheme: ((ThemeMode) -> Unit)? = null,
    onNavigate: (Screen) -> Unit,
    onCloseDrawer: () -> Unit,
    modifier: Modifier = Modifier
) {
    val colors = AppTheme.colors

    ModalDrawerSheet(
        drawerContainerColor = colors.surface,
        modifier = modifier.width(300.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
        ) {
            // Header
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(colors.softPink)
                    .statusBarsPadding()
                    .padding(20.dp)
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Box(
                        modifier = Modifier
                            .size(54.dp)
                            .clip(CircleShape)
                            .background(colors.primary),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = "👶",
                            fontSize = 28.sp
                        )
                    }
                    Spacer(modifier = Modifier.width(14.dp))
                    Column {
                        Text(
                            text = userProfile.name,
                            style = MaterialTheme.typography.titleMedium.copy(
                                fontWeight = FontWeight.Bold,
                                color = colors.textPrimary
                            )
                        )
                        Text(
                            text = "${userProfile.currentWeek} Weeks, ${userProfile.currentDay} Days",
                            style = MaterialTheme.typography.bodySmall.copy(
                                color = colors.primaryVariant,
                                fontWeight = FontWeight.SemiBold
                            )
                        )
                        Text(
                            text = "Due: ${userProfile.dueDate}",
                            style = MaterialTheme.typography.bodySmall.copy(
                                color = colors.textSecondary,
                                fontSize = 11.sp
                            )
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(12.dp))

            // Navigation Items
            DrawerNavItem("Home", Icons.Filled.Home, currentScreen == Screen.HOME) {
                onNavigate(Screen.HOME)
                onCloseDrawer()
            }
            DrawerNavItem("Pregnancy Tracker", Icons.Filled.Favorite, currentScreen == Screen.PREGNANCY_TRACKER) {
                onNavigate(Screen.PREGNANCY_TRACKER)
                onCloseDrawer()
            }
            DrawerNavItem("Appointments", Icons.Filled.Event, currentScreen == Screen.APPOINTMENTS) {
                onNavigate(Screen.APPOINTMENTS)
                onCloseDrawer()
            }
            DrawerNavItem("Vaccinations", Icons.Filled.Shield, currentScreen == Screen.VACCINES) {
                onNavigate(Screen.VACCINES)
                onCloseDrawer()
            }
            DrawerNavItem("Baby Growth", Icons.Filled.Timeline, currentScreen == Screen.BABY_GROWTH) {
                onNavigate(Screen.BABY_GROWTH)
                onCloseDrawer()
            }
            DrawerNavItem("Nutrition Advice", Icons.Filled.Restaurant, currentScreen == Screen.NUTRITION) {
                onNavigate(Screen.NUTRITION)
                onCloseDrawer()
            }
            DrawerNavItem("Health Education", Icons.Filled.MenuBook, currentScreen == Screen.HEALTH_EDUCATION) {
                onNavigate(Screen.HEALTH_EDUCATION)
                onCloseDrawer()
            }
            DrawerNavItem("Medications", Icons.Filled.Medication, currentScreen == Screen.MEDICATIONS) {
                onNavigate(Screen.MEDICATIONS)
                onCloseDrawer()
            }
            DrawerNavItem("Hospital Bag Checklist", Icons.Filled.Luggage, currentScreen == Screen.HOSPITAL_BAG) {
                onNavigate(Screen.HOSPITAL_BAG)
                onCloseDrawer()
            }
            DrawerNavItem("Contraction Timer", Icons.Filled.Timer, currentScreen == Screen.CONTRACTION_TIMER) {
                onNavigate(Screen.CONTRACTION_TIMER)
                onCloseDrawer()
            }
            DrawerNavItem("Emergency Contacts", Icons.Filled.Emergency, currentScreen == Screen.EMERGENCY_CONTACTS) {
                onNavigate(Screen.EMERGENCY_CONTACTS)
                onCloseDrawer()
            }

            HorizontalDivider(
                color = colors.borderSoft,
                modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp)
            )

            // Theme toggle inside drawer
            if (onToggleTheme != null) {
                Surface(
                    color = colors.softPurple,
                    shape = RoundedCornerShape(14.dp),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 12.dp, vertical = 4.dp)
                        .clickable {
                            val nextMode = when (themeMode) {
                                ThemeMode.SYSTEM -> ThemeMode.DARK
                                ThemeMode.DARK -> ThemeMode.LIGHT
                                ThemeMode.LIGHT -> ThemeMode.SYSTEM
                            }
                            onToggleTheme(nextMode)
                        }
                ) {
                    Row(
                        modifier = Modifier.padding(horizontal = 12.dp, vertical = 10.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(
                                imageVector = if (colors.isDark) Icons.Filled.DarkMode else Icons.Filled.LightMode,
                                contentDescription = "Theme",
                                tint = colors.accentPurple,
                                modifier = Modifier.size(20.dp)
                            )
                            Spacer(modifier = Modifier.width(10.dp))
                            Text(
                                text = "Theme: ${themeMode.name.lowercase().replaceFirstChar { it.uppercase() }}",
                                style = MaterialTheme.typography.bodyMedium.copy(
                                    fontWeight = FontWeight.SemiBold,
                                    color = colors.accentPurple,
                                    fontSize = 13.sp
                                )
                            )
                        }
                        Text(
                            text = "Switch",
                            style = MaterialTheme.typography.labelSmall.copy(
                                color = colors.primary,
                                fontWeight = FontWeight.Bold
                            )
                        )
                    }
                }
            }

            DrawerNavItem("Settings", Icons.Filled.Settings, currentScreen == Screen.SETTINGS) {
                onNavigate(Screen.SETTINGS)
                onCloseDrawer()
            }
            DrawerNavItem("Help & Support", Icons.Filled.Help, currentScreen == Screen.HELP_SUPPORT) {
                onNavigate(Screen.HELP_SUPPORT)
                onCloseDrawer()
            }
            DrawerNavItem("Log Out", Icons.Filled.ExitToApp, false) {
                onNavigate(Screen.WELCOME)
                onCloseDrawer()
            }

            Spacer(modifier = Modifier.height(24.dp))
        }
    }
}

@Composable
private fun DrawerNavItem(
    title: String,
    icon: ImageVector,
    isSelected: Boolean,
    onClick: () -> Unit
) {
    val colors = AppTheme.colors
    Surface(
        color = if (isSelected) colors.softPink.copy(alpha = 0.8f) else Color.Transparent,
        shape = RoundedCornerShape(12.dp),
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 12.dp, vertical = 2.dp)
            .clickable(onClick = onClick)
            .testTag("drawer_nav_${title.replace(" ", "_").lowercase()}")
    ) {
        Row(
            modifier = Modifier.padding(horizontal = 14.dp, vertical = 10.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                imageVector = icon,
                contentDescription = title,
                tint = if (isSelected) colors.primary else colors.textSecondary,
                modifier = Modifier.size(22.dp)
            )
            Spacer(modifier = Modifier.width(14.dp))
            Text(
                text = title,
                style = MaterialTheme.typography.bodyMedium.copy(
                    fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Medium,
                    color = if (isSelected) colors.primaryVariant else colors.textPrimary,
                    fontSize = 14.sp
                )
            )
        }
    }
}
