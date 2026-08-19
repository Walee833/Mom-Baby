package com.example.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
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
import com.example.ui.components.AppHeader
import com.example.ui.theme.*
import com.example.ui.viewmodel.ThemeMode

@Composable
fun ProfileScreen(
    userProfile: UserProfile,
    themeMode: ThemeMode = ThemeMode.SYSTEM,
    onSelectThemeMode: (ThemeMode) -> Unit = {},
    onEditProfileClick: () -> Unit,
    onLogout: () -> Unit,
    modifier: Modifier = Modifier
) {
    val colors = AppTheme.colors

    Surface(
        color = colors.background,
        modifier = modifier.fillMaxSize()
    ) {
        Column(modifier = Modifier.fillMaxSize()) {
            AppHeader(
                title = "Profile & Settings",
                rightIcon = Icons.Default.Edit,
                onRightIconClick = onEditProfileClick
            )

            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 20.dp),
                verticalArrangement = Arrangement.spacedBy(14.dp),
                contentPadding = PaddingValues(bottom = 32.dp)
            ) {
                item {
                    Surface(
                        color = colors.surface,
                        shape = RoundedCornerShape(24.dp),
                        border = androidx.compose.foundation.BorderStroke(1.dp, colors.borderSoft),
                        shadowElevation = if (colors.isDark) 0.dp else 2.dp,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Column(
                            modifier = Modifier.padding(20.dp),
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Box(
                                modifier = Modifier
                                    .size(72.dp)
                                    .clip(CircleShape)
                                    .background(colors.primary),
                                contentAlignment = Alignment.Center
                            ) {
                                Text("👩🏽‍🍼", fontSize = 36.sp)
                            }

                            Spacer(modifier = Modifier.height(12.dp))

                            Text(
                                text = userProfile.name,
                                style = MaterialTheme.typography.titleLarge.copy(
                                    fontWeight = FontWeight.Bold,
                                    color = colors.textPrimary
                                )
                            )
                            Text(
                                text = userProfile.email,
                                style = MaterialTheme.typography.bodySmall.copy(color = colors.textSecondary)
                            )
                        }
                    }
                }

                item {
                    Text(
                        text = "Appearance & Low-Light Theme",
                        style = MaterialTheme.typography.titleMedium.copy(
                            fontWeight = FontWeight.Bold,
                            color = colors.textPrimary
                        )
                    )
                }

                item {
                    Surface(
                        color = colors.surface,
                        shape = RoundedCornerShape(22.dp),
                        border = androidx.compose.foundation.BorderStroke(1.dp, colors.borderSoft),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Column(modifier = Modifier.padding(14.dp)) {
                            Text(
                                text = "Theme Mode",
                                style = MaterialTheme.typography.labelMedium.copy(
                                    fontWeight = FontWeight.Bold,
                                    color = colors.textPrimary
                                )
                            )
                            Spacer(modifier = Modifier.height(8.dp))
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.spacedBy(8.dp)
                            ) {
                                ThemeModeChip(
                                    title = "System",
                                    icon = Icons.Default.BrightnessAuto,
                                    isSelected = themeMode == ThemeMode.SYSTEM,
                                    onClick = { onSelectThemeMode(ThemeMode.SYSTEM) },
                                    modifier = Modifier.weight(1f)
                                )
                                ThemeModeChip(
                                    title = "Light",
                                    icon = Icons.Default.LightMode,
                                    isSelected = themeMode == ThemeMode.LIGHT,
                                    onClick = { onSelectThemeMode(ThemeMode.LIGHT) },
                                    modifier = Modifier.weight(1f)
                                )
                                ThemeModeChip(
                                    title = "Dark",
                                    icon = Icons.Default.DarkMode,
                                    isSelected = themeMode == ThemeMode.DARK,
                                    onClick = { onSelectThemeMode(ThemeMode.DARK) },
                                    modifier = Modifier.weight(1f)
                                )
                            }
                        }
                    }
                }

                item {
                    Text(
                        text = "Pregnancy & Hospital Information",
                        style = MaterialTheme.typography.titleMedium.copy(
                            fontWeight = FontWeight.Bold,
                            color = colors.textPrimary
                        )
                    )
                }

                item {
                    Surface(
                        color = colors.surface,
                        shape = RoundedCornerShape(22.dp),
                        border = androidx.compose.foundation.BorderStroke(1.dp, colors.borderSoft),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Column(modifier = Modifier.padding(16.dp)) {
                            InfoRow(icon = Icons.Default.CalendarToday, label = "Estimated Due Date", value = userProfile.dueDate)
                            InfoRow(icon = Icons.Default.Favorite, label = "Baby Nickname", value = userProfile.babyNickname)
                            InfoRow(icon = Icons.Default.MedicalServices, label = "Obstetrician", value = userProfile.doctorName)
                            InfoRow(icon = Icons.Default.LocalHospital, label = "Delivery Center", value = userProfile.hospitalName)
                            InfoRow(icon = Icons.Default.Person, label = "Partner Support", value = "${userProfile.partnerName} (${userProfile.partnerPhone})")
                        }
                    }
                }

                item {
                    Text(
                        text = "General App Settings",
                        style = MaterialTheme.typography.titleMedium.copy(
                            fontWeight = FontWeight.Bold,
                            color = colors.textPrimary
                        )
                    )
                }

                item {
                    Surface(
                        color = colors.surface,
                        shape = RoundedCornerShape(22.dp),
                        border = androidx.compose.foundation.BorderStroke(1.dp, colors.borderSoft),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Column(modifier = Modifier.padding(8.dp)) {
                            SettingNavRow(icon = Icons.Default.NotificationsNone, title = "Push Reminders & Alerts") {}
                            SettingNavRow(icon = Icons.Default.Security, title = "Data Privacy & HIPAA Compliance") {}
                            SettingNavRow(icon = Icons.Default.HelpOutline, title = "Maternal Health FAQ & Support") {}
                        }
                    }
                }

                item {
                    Button(
                        onClick = onLogout,
                        colors = ButtonDefaults.buttonColors(containerColor = colors.emergencyRed.copy(alpha = 0.15f)),
                        shape = RoundedCornerShape(20.dp),
                        modifier = Modifier.fillMaxWidth().testTag("profile_logout_btn")
                    ) {
                        Text("Log Out", color = colors.emergencyRed, fontWeight = FontWeight.Bold)
                    }
                }
            }
        }
    }
}

@Composable
private fun ThemeModeChip(
    title: String,
    icon: ImageVector,
    isSelected: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val colors = AppTheme.colors
    Surface(
        color = if (isSelected) colors.primary else colors.softPink,
        shape = RoundedCornerShape(14.dp),
        modifier = modifier
            .clickable(onClick = onClick)
            .height(44.dp)
            .testTag("theme_chip_${title.lowercase()}")
    ) {
        Row(
            modifier = Modifier.fillMaxSize().padding(horizontal = 8.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            Icon(
                imageVector = icon,
                contentDescription = title,
                tint = if (isSelected) (if (colors.isDark) colors.background else Color.White) else colors.textPrimary,
                modifier = Modifier.size(16.dp)
            )
            Spacer(modifier = Modifier.width(6.dp))
            Text(
                text = title,
                style = MaterialTheme.typography.labelSmall.copy(
                    fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Medium,
                    color = if (isSelected) (if (colors.isDark) colors.background else Color.White) else colors.textPrimary,
                    fontSize = 11.sp
                )
            )
        }
    }
}

@Composable
private fun InfoRow(
    icon: ImageVector,
    label: String,
    value: String
) {
    val colors = AppTheme.colors
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            modifier = Modifier
                .size(34.dp)
                .clip(CircleShape)
                .background(colors.softPink),
            contentAlignment = Alignment.Center
        ) {
            Icon(imageVector = icon, contentDescription = null, tint = colors.primary, modifier = Modifier.size(18.dp))
        }

        Spacer(modifier = Modifier.width(12.dp))

        Column {
            Text(text = label, style = MaterialTheme.typography.labelSmall.copy(color = colors.textSecondary, fontSize = 11.sp))
            Text(text = value, style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.Bold, color = colors.textPrimary, fontSize = 13.sp))
        }
    }
}

@Composable
private fun SettingNavRow(
    icon: ImageVector,
    title: String,
    onClick: () -> Unit
) {
    val colors = AppTheme.colors
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick)
            .padding(horizontal = 12.dp, vertical = 12.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Icon(imageVector = icon, contentDescription = null, tint = colors.primaryVariant, modifier = Modifier.size(20.dp))
            Spacer(modifier = Modifier.width(12.dp))
            Text(text = title, style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.Medium, color = colors.textPrimary, fontSize = 13.sp))
        }
        Icon(imageVector = Icons.Default.ChevronRight, contentDescription = null, tint = colors.textSecondary)
    }
}
