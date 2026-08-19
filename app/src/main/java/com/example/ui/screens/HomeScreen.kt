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
import com.example.ui.components.MetricItemCard
import com.example.ui.theme.*
import com.example.ui.viewmodel.PregnancyWeekInfo
import com.example.ui.viewmodel.Screen

@Composable
fun HomeScreen(
    userProfile: UserProfile,
    weekInfo: PregnancyWeekInfo,
    unreadNotificationsCount: Int,
    onOpenDrawer: () -> Unit,
    onOpenNotifications: () -> Unit,
    onNavigate: (Screen) -> Unit,
    modifier: Modifier = Modifier
) {
    val colors = AppTheme.colors

    Surface(
        color = colors.background,
        modifier = modifier.fillMaxSize()
    ) {
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .statusBarsPadding()
                .padding(horizontal = 20.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp),
            contentPadding = PaddingValues(top = 12.dp, bottom = 24.dp)
        ) {
            // Top App Bar
            item {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.clickable { onOpenDrawer() }
                    ) {
                        IconButton(onClick = onOpenDrawer, modifier = Modifier.testTag("home_drawer_btn")) {
                            Icon(Icons.Default.Menu, contentDescription = "Menu", tint = colors.textPrimary)
                        }
                        Spacer(modifier = Modifier.width(6.dp))
                        Column {
                            Text(
                                text = "Hello, ${userProfile.name.split(" ").firstOrNull() ?: "Blessing"} 👋",
                                style = MaterialTheme.typography.titleMedium.copy(
                                    fontWeight = FontWeight.Bold,
                                    color = colors.textPrimary
                                )
                            )
                            Text(
                                text = "Due in ${weekInfo.daysRemaining} days · ${weekInfo.trimester}nd Trimester",
                                style = MaterialTheme.typography.bodySmall.copy(
                                    color = colors.textSecondary,
                                    fontSize = 12.sp
                                )
                            )
                        }
                    }

                    Box {
                        IconButton(
                            onClick = onOpenNotifications,
                            modifier = Modifier.testTag("home_notifications_btn")
                        ) {
                            Icon(
                                imageVector = Icons.Default.NotificationsNone,
                                contentDescription = "Notifications",
                                tint = colors.textPrimary
                            )
                        }
                        if (unreadNotificationsCount > 0) {
                            Badge(
                                containerColor = colors.emergencyRed,
                                modifier = Modifier
                                    .align(Alignment.TopEnd)
                                    .padding(top = 6.dp, end = 6.dp)
                            ) {
                                Text("$unreadNotificationsCount", color = Color.White, fontSize = 10.sp)
                            }
                        }
                    }
                }
            }

            // Baby Weekly Milestone Hero Card
            item {
                Surface(
                    color = colors.surface,
                    shape = RoundedCornerShape(28.dp),
                    border = androidx.compose.foundation.BorderStroke(1.dp, colors.borderSoft),
                    shadowElevation = if (colors.isDark) 0.dp else 4.dp,
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable { onNavigate(Screen.PREGNANCY_TRACKER) }
                        .testTag("hero_pregnancy_card")
                ) {
                    Column(
                        modifier = Modifier.padding(20.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Surface(
                                color = colors.softPink,
                                shape = RoundedCornerShape(12.dp)
                            ) {
                                Text(
                                    text = "Week ${weekInfo.week} of 40",
                                    style = MaterialTheme.typography.labelSmall.copy(
                                        color = colors.primaryVariant,
                                        fontWeight = FontWeight.Bold
                                    ),
                                    modifier = Modifier.padding(horizontal = 10.dp, vertical = 4.dp)
                                )
                            }

                            Text(
                                text = "Details →",
                                style = MaterialTheme.typography.labelSmall.copy(
                                    color = colors.primary,
                                    fontWeight = FontWeight.Bold
                                )
                            )
                        }

                        Spacer(modifier = Modifier.height(14.dp))

                        Box(
                            modifier = Modifier
                                .size(140.dp)
                                .clip(CircleShape)
                                .background(colors.softPink),
                            contentAlignment = Alignment.Center
                        ) {
                            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                                Text(text = weekInfo.emoji, fontSize = 48.sp)
                                Text(
                                    text = weekInfo.fruitOrVeggie,
                                    style = MaterialTheme.typography.labelMedium.copy(
                                        color = colors.primaryVariant,
                                        fontWeight = FontWeight.Bold
                                    )
                                )
                            }
                        }

                        Spacer(modifier = Modifier.height(12.dp))

                        Text(
                            text = "Baby is the size of an ear of ${weekInfo.fruitOrVeggie}!",
                            style = MaterialTheme.typography.titleMedium.copy(
                                fontWeight = FontWeight.Bold,
                                color = colors.textPrimary
                            )
                        )

                        Spacer(modifier = Modifier.height(14.dp))

                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            MetricItemCard(label = "Weight", value = weekInfo.weight, modifier = Modifier.weight(1f))
                            MetricItemCard(label = "Length", value = weekInfo.length, modifier = Modifier.weight(1f))
                            MetricItemCard(label = "Progress", value = "${weekInfo.progressPercent}%", modifier = Modifier.weight(1f))
                        }
                    }
                }
            }

            // Quick Actions Header
            item {
                Text(
                    text = "Quick Actions",
                    style = MaterialTheme.typography.titleMedium.copy(
                        fontWeight = FontWeight.Bold,
                        color = colors.textPrimary
                    )
                )
            }

            // Quick Action Grid 2x2
            item {
                Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(10.dp)
                    ) {
                        QuickActionCard(
                            title = "Pregnancy Tracker",
                            subtitle = "Week & kicks",
                            icon = Icons.Default.Favorite,
                            iconBg = colors.softPink,
                            iconTint = colors.primary,
                            modifier = Modifier.weight(1f),
                            onClick = { onNavigate(Screen.PREGNANCY_TRACKER) }
                        )
                        QuickActionCard(
                            title = "Appointments",
                            subtitle = "Visits & labs",
                            icon = Icons.Default.CalendarMonth,
                            iconBg = colors.lightBlue,
                            iconTint = colors.infoBlue,
                            modifier = Modifier.weight(1f),
                            onClick = { onNavigate(Screen.APPOINTMENTS) }
                        )
                    }

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(10.dp)
                    ) {
                        QuickActionCard(
                            title = "Vaccinations",
                            subtitle = "Immunity schedule",
                            icon = Icons.Default.Shield,
                            iconBg = colors.lightGreen,
                            iconTint = colors.successGreen,
                            modifier = Modifier.weight(1f),
                            onClick = { onNavigate(Screen.VACCINES) }
                        )
                        QuickActionCard(
                            title = "Baby Growth",
                            subtitle = "Weight & milestones",
                            icon = Icons.Default.Timeline,
                            iconBg = colors.softPurple,
                            iconTint = colors.accentPurple,
                            modifier = Modifier.weight(1f),
                            onClick = { onNavigate(Screen.BABY_GROWTH) }
                        )
                    }

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(10.dp)
                    ) {
                        QuickActionCard(
                            title = "Hospital Bag",
                            subtitle = "Packing checklist",
                            icon = Icons.Default.Luggage,
                            iconBg = colors.lightOrange,
                            iconTint = colors.warningOrange,
                            modifier = Modifier.weight(1f),
                            onClick = { onNavigate(Screen.HOSPITAL_BAG) }
                        )
                        QuickActionCard(
                            title = "Contraction Timer",
                            subtitle = "Labor tracking",
                            icon = Icons.Default.Timer,
                            iconBg = colors.lightRed,
                            iconTint = colors.emergencyRed,
                            modifier = Modifier.weight(1f),
                            onClick = { onNavigate(Screen.CONTRACTION_TIMER) }
                        )
                    }
                }
            }

            // Next Appointment Banner
            item {
                Surface(
                    color = colors.surface,
                    shape = RoundedCornerShape(20.dp),
                    border = androidx.compose.foundation.BorderStroke(1.dp, colors.borderSoft),
                    shadowElevation = if (colors.isDark) 0.dp else 2.dp,
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable { onNavigate(Screen.APPOINTMENTS) }
                ) {
                    Row(
                        modifier = Modifier.padding(16.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Surface(
                            color = colors.softPink,
                            shape = RoundedCornerShape(14.dp),
                            modifier = Modifier.size(50.dp)
                        ) {
                            Column(
                                horizontalAlignment = Alignment.CenterHorizontally,
                                verticalArrangement = Arrangement.Center
                            ) {
                                Text("OCT", style = MaterialTheme.typography.labelSmall.copy(fontWeight = FontWeight.Bold, color = colors.primary, fontSize = 10.sp))
                                Text("24", style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold, color = colors.primaryVariant))
                            }
                        }

                        Spacer(modifier = Modifier.width(14.dp))

                        Column(modifier = Modifier.weight(1f)) {
                            Text(
                                text = "Next Prenatal Appointment",
                                style = MaterialTheme.typography.labelSmall.copy(
                                    color = colors.textSecondary,
                                    fontSize = 11.sp
                                )
                            )
                            Text(
                                text = "24-Week Anomaly Ultrasound",
                                style = MaterialTheme.typography.titleMedium.copy(
                                    fontWeight = FontWeight.Bold,
                                    color = colors.textPrimary,
                                    fontSize = 14.sp
                                )
                            )
                            Text(
                                text = "Dr. Adaeze Johnson · 10:30 AM",
                                style = MaterialTheme.typography.bodySmall.copy(
                                    color = colors.primary,
                                    fontSize = 12.sp
                                )
                            )
                        }

                        Icon(
                            imageVector = Icons.Default.ChevronRight,
                            contentDescription = null,
                            tint = colors.textSecondary
                        )
                    }
                }
            }

            // Health Education & Nutrition banner
            item {
                Surface(
                    color = colors.softPurple,
                    shape = RoundedCornerShape(20.dp),
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable { onNavigate(Screen.HEALTH_EDUCATION) }
                ) {
                    Row(
                        modifier = Modifier.padding(16.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(text = "📚", fontSize = 28.sp)
                        Spacer(modifier = Modifier.width(12.dp))
                        Column(modifier = Modifier.weight(1f)) {
                            Text(
                                text = "Health Education & Nutrition",
                                style = MaterialTheme.typography.titleMedium.copy(
                                    fontWeight = FontWeight.Bold,
                                    color = colors.accentPurple,
                                    fontSize = 14.sp
                                )
                            )
                            Text(
                                text = "Explore expert articles on safe labor, newborn care & meal guides.",
                                style = MaterialTheme.typography.bodySmall.copy(
                                    color = colors.textSecondary,
                                    fontSize = 11.sp
                                )
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun QuickActionCard(
    title: String,
    subtitle: String,
    icon: ImageVector,
    iconBg: Color,
    iconTint: Color,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val colors = AppTheme.colors
    Surface(
        color = colors.surface,
        shape = RoundedCornerShape(18.dp),
        border = androidx.compose.foundation.BorderStroke(1.dp, colors.borderSoft),
        shadowElevation = if (colors.isDark) 0.dp else 2.dp,
        modifier = modifier
            .clickable(onClick = onClick)
            .testTag("action_${title.replace(" ", "_").lowercase()}")
    ) {
        Row(
            modifier = Modifier.padding(12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .size(40.dp)
                    .clip(RoundedCornerShape(12.dp))
                    .background(iconBg),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = icon,
                    contentDescription = null,
                    tint = iconTint,
                    modifier = Modifier.size(22.dp)
                )
            }

            Spacer(modifier = Modifier.width(10.dp))

            Column {
                Text(
                    text = title,
                    style = MaterialTheme.typography.titleMedium.copy(
                        fontWeight = FontWeight.Bold,
                        color = colors.textPrimary,
                        fontSize = 13.sp
                    )
                )
                Text(
                    text = subtitle,
                    style = MaterialTheme.typography.bodySmall.copy(
                        color = colors.textSecondary,
                        fontSize = 11.sp
                    )
                )
            }
        }
    }
}
