package com.example.ui.screens

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ui.components.AppHeader
import com.example.ui.components.MetricItemCard
import com.example.ui.components.SegmentedTabBar
import com.example.ui.theme.*
import com.example.ui.viewmodel.PregnancyWeekInfo

@Composable
fun PregnancyTrackerScreen(
    weekInfo: PregnancyWeekInfo,
    kickCount: Int,
    onIncrementKick: () -> Unit,
    onResetKicks: () -> Unit,
    onSelectWeek: (Int) -> Unit,
    onBack: () -> Unit,
    modifier: Modifier = Modifier
) {
    val colors = AppTheme.colors
    var selectedTab by remember { mutableIntStateOf(0) }
    val tabs = listOf("Overview", "Baby Development", "Body Changes")

    Surface(
        color = colors.background,
        modifier = modifier.fillMaxSize()
    ) {
        Column(modifier = Modifier.fillMaxSize()) {
            AppHeader(
                title = "Pregnancy Tracker",
                onBackClick = onBack
            )

            Spacer(modifier = Modifier.height(12.dp))

            SegmentedTabBar(
                tabs = tabs,
                selectedIndex = selectedTab,
                onTabSelected = { selectedTab = it },
                modifier = Modifier.testTag("tracker_tabs")
            )

            Spacer(modifier = Modifier.height(12.dp))

            when (selectedTab) {
                0 -> OverviewTabContent(weekInfo = weekInfo)
                1 -> BabyDevelopmentTabContent(
                    weekInfo = weekInfo,
                    kickCount = kickCount,
                    onIncrementKick = onIncrementKick,
                    onResetKicks = onResetKicks,
                    onSelectWeek = onSelectWeek
                )
                2 -> BodyChangesTabContent()
            }
        }
    }
}

@Composable
private fun OverviewTabContent(
    weekInfo: PregnancyWeekInfo
) {
    val colors = AppTheme.colors
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 20.dp),
        verticalArrangement = Arrangement.spacedBy(14.dp),
        contentPadding = PaddingValues(bottom = 32.dp)
    ) {
        item {
            Column(modifier = Modifier.padding(top = 4.dp)) {
                Text(
                    text = "${weekInfo.week} Weeks, 3 Days",
                    style = MaterialTheme.typography.headlineMedium.copy(
                        fontWeight = FontWeight.Bold,
                        color = colors.textPrimary
                    )
                )
                Text(
                    text = "${weekInfo.trimester}nd Trimester · ${weekInfo.daysRemaining} days to go",
                    style = MaterialTheme.typography.bodyMedium.copy(
                        color = colors.textSecondary
                    )
                )
            }
        }

        item {
            Surface(
                color = colors.surface,
                shape = RoundedCornerShape(24.dp),
                border = androidx.compose.foundation.BorderStroke(1.dp, colors.borderSoft),
                shadowElevation = if (colors.isDark) 0.dp else 2.dp,
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(
                    modifier = Modifier.padding(18.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Box(
                        modifier = Modifier
                            .size(120.dp)
                            .clip(CircleShape)
                            .background(colors.softPink),
                        contentAlignment = Alignment.Center
                    ) {
                        Column(horizontalAlignment = Alignment.CenterHorizontally) {
                            Text(text = weekInfo.emoji, fontSize = 42.sp)
                            Text(
                                text = weekInfo.fruitOrVeggie,
                                style = MaterialTheme.typography.labelSmall.copy(
                                    fontWeight = FontWeight.Bold,
                                    color = colors.primaryVariant
                                )
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(14.dp))

                    Text(
                        text = "Baby is as big as an ear of ${weekInfo.fruitOrVeggie}!",
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
                        MetricItemCard(label = "Length", value = weekInfo.length, modifier = Modifier.weight(1f))
                        MetricItemCard(label = "Weight", value = weekInfo.weight, modifier = Modifier.weight(1f))
                        MetricItemCard(label = "Progress", value = "${weekInfo.progressPercent}%", modifier = Modifier.weight(1f))
                    }
                }
            }
        }

        item {
            Surface(
                color = colors.surface,
                shape = RoundedCornerShape(20.dp),
                border = androidx.compose.foundation.BorderStroke(1.dp, colors.borderSoft),
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text(
                        text = "Milestone Highlights",
                        style = MaterialTheme.typography.titleMedium.copy(
                            fontWeight = FontWeight.Bold,
                            color = colors.textPrimary
                        )
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    weekInfo.highlights.forEach { highlight ->
                        Row(
                            modifier = Modifier.padding(vertical = 3.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Box(
                                modifier = Modifier
                                    .size(6.dp)
                                    .clip(CircleShape)
                                    .background(colors.primary)
                            )
                            Spacer(modifier = Modifier.width(8.dp))
                            Text(
                                text = highlight,
                                style = MaterialTheme.typography.bodyMedium.copy(
                                    color = colors.textSecondary,
                                    fontSize = 13.sp
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
private fun BabyDevelopmentTabContent(
    weekInfo: PregnancyWeekInfo,
    kickCount: Int,
    onIncrementKick: () -> Unit,
    onResetKicks: () -> Unit,
    onSelectWeek: (Int) -> Unit
) {
    val colors = AppTheme.colors
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
                shape = RoundedCornerShape(22.dp),
                border = androidx.compose.foundation.BorderStroke(1.dp, colors.borderSoft),
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text(
                        text = "Weekly Developmental Summary",
                        style = MaterialTheme.typography.titleMedium.copy(
                            fontWeight = FontWeight.Bold,
                            color = colors.textPrimary
                        )
                    )
                    Spacer(modifier = Modifier.height(6.dp))
                    Text(
                        text = weekInfo.description,
                        style = MaterialTheme.typography.bodyMedium.copy(
                            color = colors.textSecondary,
                            lineHeight = 20.sp
                        )
                    )
                }
            }
        }

        item {
            Surface(
                color = colors.surface,
                shape = RoundedCornerShape(24.dp),
                border = androidx.compose.foundation.BorderStroke(1.dp, colors.borderSoft),
                shadowElevation = if (colors.isDark) 0.dp else 3.dp,
                modifier = Modifier.fillMaxWidth().testTag("kick_counter_section")
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
                        Column {
                            Text(
                                text = "Fetal Kick Counter 👣",
                                style = MaterialTheme.typography.titleMedium.copy(
                                    fontWeight = FontWeight.Bold,
                                    color = colors.textPrimary
                                )
                            )
                            Text(
                                text = "Recommended: 10 kicks in 2 hours",
                                style = MaterialTheme.typography.bodySmall.copy(color = colors.textSecondary)
                            )
                        }

                        TextButton(onClick = onResetKicks) {
                            Text("Reset", color = colors.primary, fontSize = 12.sp, fontWeight = FontWeight.Bold)
                        }
                    }

                    Spacer(modifier = Modifier.height(14.dp))

                    Text(
                        text = "$kickCount / 10",
                        style = MaterialTheme.typography.headlineLarge.copy(
                            fontWeight = FontWeight.Bold,
                            color = colors.primary,
                            fontSize = 42.sp
                        )
                    )

                    Spacer(modifier = Modifier.height(12.dp))

                    Button(
                        onClick = onIncrementKick,
                        colors = ButtonDefaults.buttonColors(containerColor = colors.primary),
                        shape = RoundedCornerShape(22.dp),
                        modifier = Modifier
                            .fillMaxWidth(0.7f)
                            .height(48.dp)
                            .testTag("tap_kick_button")
                    ) {
                        Text(
                            text = "+ Tap to Record Kick",
                            color = if (colors.isDark) colors.background else Color.White,
                            fontWeight = FontWeight.Bold
                        )
                    }
                }
            }
        }

        item {
            Text(
                text = "Select Week to Explore",
                style = MaterialTheme.typography.titleMedium.copy(
                    fontWeight = FontWeight.Bold,
                    color = colors.textPrimary
                )
            )
        }

        item {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                listOf(8, 16, 24, 32, 40).forEach { wk ->
                    val isSelected = wk == weekInfo.week
                    Surface(
                        color = if (isSelected) colors.primary else colors.surface,
                        shape = RoundedCornerShape(14.dp),
                        border = androidx.compose.foundation.BorderStroke(1.dp, if (isSelected) colors.primary else colors.borderSoft),
                        modifier = Modifier
                            .weight(1f)
                            .clickable { onSelectWeek(wk) }
                    ) {
                        Column(
                            modifier = Modifier.padding(vertical = 10.dp),
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Text(
                                text = "Wk",
                                style = MaterialTheme.typography.labelSmall.copy(
                                    color = if (isSelected) (if (colors.isDark) colors.background else Color.White) else colors.textSecondary,
                                    fontSize = 10.sp
                                )
                            )
                            Text(
                                text = "$wk",
                                style = MaterialTheme.typography.titleMedium.copy(
                                    fontWeight = FontWeight.Bold,
                                    color = if (isSelected) (if (colors.isDark) colors.background else Color.White) else colors.textPrimary
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
private fun BodyChangesTabContent() {
    val colors = AppTheme.colors
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 20.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp),
        contentPadding = PaddingValues(bottom = 32.dp)
    ) {
        item {
            Text(
                text = "What You Might Experience This Week",
                style = MaterialTheme.typography.titleMedium.copy(
                    fontWeight = FontWeight.Bold,
                    color = colors.textPrimary
                )
            )
        }

        item {
            BodyChangeItem(
                emoji = "🌸",
                title = "Round Ligament Pain",
                desc = "Mild pulling or aching sensations in the lower abdomen as supporting uterine ligaments stretch."
            )
        }
        item {
            BodyChangeItem(
                emoji = "💧",
                title = "Increased Blood Volume",
                desc = "Your circulating blood volume increases up to 50% to nourish baby. Stay well-hydrated!"
            )
        }
        item {
            BodyChangeItem(
                emoji = "✨",
                title = "Pregnancy Glow & Thicker Hair",
                desc = "Elevated estrogen levels decrease hair shedding, leaving hair fuller and radiant."
            )
        }
        item {
            BodyChangeItem(
                emoji = "💤",
                title = "Vivid Dreams & Sleep Adjustments",
                desc = "Hormonal surges can cause deep, vivid dreaming. Use a pregnancy pillow for comfortable side sleeping."
            )
        }
    }
}

@Composable
private fun BodyChangeItem(
    emoji: String,
    title: String,
    desc: String
) {
    val colors = AppTheme.colors
    Surface(
        color = colors.surface,
        shape = RoundedCornerShape(18.dp),
        border = androidx.compose.foundation.BorderStroke(1.dp, colors.borderSoft),
        modifier = Modifier.fillMaxWidth()
    ) {
        Row(modifier = Modifier.padding(14.dp), verticalAlignment = Alignment.Top) {
            Text(text = emoji, fontSize = 24.sp)
            Spacer(modifier = Modifier.width(12.dp))
            Column {
                Text(
                    text = title,
                    style = MaterialTheme.typography.titleMedium.copy(
                        fontWeight = FontWeight.Bold,
                        color = colors.textPrimary,
                        fontSize = 14.sp
                    )
                )
                Spacer(modifier = Modifier.height(2.dp))
                Text(
                    text = desc,
                    style = MaterialTheme.typography.bodySmall.copy(
                        color = colors.textSecondary,
                        fontSize = 12.sp,
                        lineHeight = 17.sp
                    )
                )
            }
        }
    }
}
