package com.example.ui.screens

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.model.BabyGrowthRecord
import com.example.data.model.BabyMilestone
import com.example.ui.components.AppHeader
import com.example.ui.components.MetricItemCard
import com.example.ui.components.SegmentedTabBar
import com.example.ui.theme.*

@Composable
fun BabyGrowthScreen(
    growthRecords: List<BabyGrowthRecord>,
    milestones: List<BabyMilestone>,
    onAddGrowthRecord: () -> Unit,
    onToggleMilestone: (String) -> Unit,
    onBack: () -> Unit,
    modifier: Modifier = Modifier
) {
    var selectedTab by remember { mutableIntStateOf(0) }
    val tabs = listOf("Weight Chart", "Milestones", "Log History")

    val latest = growthRecords.lastOrNull() ?: BabyGrowthRecord("0", "Birth", 3.2f, 49.5f, 34.0f, 13.1f, "Day 0")

    Surface(
        color = BackgroundLight,
        modifier = modifier.fillMaxSize()
    ) {
        Column(modifier = Modifier.fillMaxSize()) {
            AppHeader(
                title = "Baby Growth Tracking",
                onBackClick = onBack,
                rightIcon = Icons.Default.Add,
                onRightIconClick = onAddGrowthRecord
            )

            Spacer(modifier = Modifier.height(6.dp))

            // Latest stats summary
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                MetricItemCard(label = "Latest Weight", value = "${latest.weightKg} kg", modifier = Modifier.weight(1f))
                MetricItemCard(label = "Length/Height", value = "${latest.heightCm} cm", modifier = Modifier.weight(1f))
                MetricItemCard(label = "Head Circ.", value = "${latest.headCircumferenceCm} cm", modifier = Modifier.weight(1f))
            }

            Spacer(modifier = Modifier.height(14.dp))

            SegmentedTabBar(
                tabs = tabs,
                selectedIndex = selectedTab,
                onTabSelected = { selectedTab = it },
                modifier = Modifier.testTag("growth_tabs")
            )

            Spacer(modifier = Modifier.height(14.dp))

            when (selectedTab) {
                0 -> WeightChartTab(growthRecords = growthRecords)
                1 -> MilestonesTab(milestones = milestones, onToggleMilestone = onToggleMilestone)
                2 -> LogHistoryTab(growthRecords = growthRecords)
            }
        }
    }
}

@Composable
private fun WeightChartTab(growthRecords: List<BabyGrowthRecord>) {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 20.dp),
        verticalArrangement = Arrangement.spacedBy(14.dp),
        contentPadding = PaddingValues(bottom = 32.dp)
    ) {
        item {
            Surface(
                color = SurfaceWhite,
                shape = RoundedCornerShape(24.dp),
                border = androidx.compose.foundation.BorderStroke(1.dp, SoftPink),
                shadowElevation = 2.dp,
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(modifier = Modifier.padding(18.dp)) {
                    Text(
                        text = "WHO Standard Growth Curve (kg)",
                        style = MaterialTheme.typography.titleMedium.copy(
                            fontWeight = FontWeight.Bold,
                            color = TextPrimary
                        )
                    )
                    Text(
                        text = "Steady percentiles between 50th - 85th band",
                        style = MaterialTheme.typography.bodySmall.copy(color = TextSecondary)
                    )

                    Spacer(modifier = Modifier.height(20.dp))

                    // Custom Canvas Chart
                    Canvas(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(160.dp)
                    ) {
                        val width = size.width
                        val height = size.height
                        val points = growthRecords.map { it.weightKg }
                        if (points.isEmpty()) return@Canvas

                        val minVal = 2.5f
                        val maxVal = 10.0f

                        // Draw Grid lines
                        for (i in 0..4) {
                            val y = height * (i / 4f)
                            drawLine(
                                color = SoftPink,
                                start = Offset(0f, y),
                                end = Offset(width, y),
                                strokeWidth = 1.5f
                            )
                        }

                        val stepX = width / (points.size - 1).coerceAtLeast(1)
                        val path = Path()

                        points.forEachIndexed { index, value ->
                            val x = index * stepX
                            val normalizedY = 1f - ((value - minVal) / (maxVal - minVal)).coerceIn(0f, 1f)
                            val y = normalizedY * height

                            if (index == 0) path.moveTo(x, y) else path.lineTo(x, y)

                            // Point circle
                            drawCircle(
                                color = PrimaryPink,
                                radius = 5.dp.toPx(),
                                center = Offset(x, y)
                            )
                            drawCircle(
                                color = Color.White,
                                radius = 2.5.dp.toPx(),
                                center = Offset(x, y)
                            )
                        }

                        drawPath(
                            path = path,
                            color = PrimaryPink,
                            style = Stroke(width = 3.dp.toPx())
                        )
                    }

                    Spacer(modifier = Modifier.height(12.dp))

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        growthRecords.forEach {
                            Text(
                                text = it.period,
                                style = MaterialTheme.typography.labelSmall.copy(
                                    color = TextSecondary,
                                    fontSize = 10.sp
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
private fun MilestonesTab(
    milestones: List<BabyMilestone>,
    onToggleMilestone: (String) -> Unit
) {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 20.dp),
        verticalArrangement = Arrangement.spacedBy(10.dp),
        contentPadding = PaddingValues(bottom = 32.dp)
    ) {
        items(milestones, key = { it.id }) { item ->
            Surface(
                color = SurfaceWhite,
                shape = RoundedCornerShape(18.dp),
                border = androidx.compose.foundation.BorderStroke(1.dp, if (item.isAchieved) LightGreen else SoftPink),
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { onToggleMilestone(item.id) }
            ) {
                Row(
                    modifier = Modifier.padding(14.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Box(
                        modifier = Modifier
                            .size(26.dp)
                            .clip(CircleShape)
                            .background(if (item.isAchieved) SuccessGreen else SoftPink),
                        contentAlignment = Alignment.Center
                    ) {
                        if (item.isAchieved) {
                            Icon(Icons.Default.Check, contentDescription = null, tint = Color.White, modifier = Modifier.size(16.dp))
                        }
                    }

                    Spacer(modifier = Modifier.width(12.dp))

                    Column(modifier = Modifier.weight(1f)) {
                        Text(
                            text = item.title,
                            style = MaterialTheme.typography.titleMedium.copy(
                                fontWeight = FontWeight.Bold,
                                color = TextPrimary,
                                fontSize = 13.sp
                            )
                        )
                        Text(
                            text = "${item.category} · ${item.ageRange}",
                            style = MaterialTheme.typography.bodySmall.copy(
                                color = PrimaryPinkDark,
                                fontSize = 11.sp
                            )
                        )
                        if (item.description.isNotBlank()) {
                            Text(
                                text = item.description,
                                style = MaterialTheme.typography.bodySmall.copy(
                                    color = TextSecondary,
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
private fun LogHistoryTab(growthRecords: List<BabyGrowthRecord>) {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 20.dp),
        verticalArrangement = Arrangement.spacedBy(10.dp),
        contentPadding = PaddingValues(bottom = 32.dp)
    ) {
        items(growthRecords.reversed(), key = { it.id }) { record ->
            Surface(
                color = SurfaceWhite,
                shape = RoundedCornerShape(18.dp),
                border = androidx.compose.foundation.BorderStroke(1.dp, SoftPink),
                modifier = Modifier.fillMaxWidth()
            ) {
                Row(
                    modifier = Modifier.padding(14.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Column {
                        Text(
                            text = record.period,
                            style = MaterialTheme.typography.titleMedium.copy(
                                fontWeight = FontWeight.Bold,
                                color = TextPrimary
                            )
                        )
                        Text(
                            text = "Date: ${record.recordedDate}",
                            style = MaterialTheme.typography.bodySmall.copy(color = TextSecondary, fontSize = 11.sp)
                        )
                    }

                    Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                        Text(
                            text = "${record.weightKg} kg",
                            style = MaterialTheme.typography.titleMedium.copy(
                                fontWeight = FontWeight.Bold,
                                color = PrimaryPink
                            )
                        )
                        Text(
                            text = "${record.heightCm} cm",
                            style = MaterialTheme.typography.titleMedium.copy(
                                fontWeight = FontWeight.SemiBold,
                                color = AccentPurple
                            )
                        )
                    }
                }
            }
        }
    }
}
