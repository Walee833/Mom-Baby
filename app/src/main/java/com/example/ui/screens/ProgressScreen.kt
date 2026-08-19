package com.example.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ui.components.AppHeader
import com.example.ui.components.MetricItemCard
import com.example.ui.theme.*
import com.example.ui.viewmodel.PregnancyWeekInfo

@Composable
fun ProgressScreen(
    weekInfo: PregnancyWeekInfo,
    kickCount: Int,
    waterGlasses: Int,
    modifier: Modifier = Modifier
) {
    Surface(
        color = BackgroundLight,
        modifier = modifier.fillMaxSize()
    ) {
        Column(modifier = Modifier.fillMaxSize()) {
            AppHeader(
                title = "Progress & Stats"
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
                        color = SurfaceWhite,
                        shape = RoundedCornerShape(24.dp),
                        border = androidx.compose.foundation.BorderStroke(1.dp, SoftPink),
                        shadowElevation = 3.dp,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Column(modifier = Modifier.padding(18.dp)) {
                            Text(
                                text = "Pregnancy Completion",
                                style = MaterialTheme.typography.titleMedium.copy(
                                    fontWeight = FontWeight.Bold,
                                    color = TextPrimary
                                )
                            )
                            Spacer(modifier = Modifier.height(8.dp))
                            LinearProgressIndicator(
                                progress = { weekInfo.progressPercent / 100f },
                                color = PrimaryPink,
                                trackColor = SoftPink,
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(10.dp)
                                    .clip(RoundedCornerShape(5.dp))
                            )
                            Spacer(modifier = Modifier.height(6.dp))
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween
                            ) {
                                Text(text = "Week ${weekInfo.week} of 40", style = MaterialTheme.typography.bodySmall.copy(color = TextSecondary, fontSize = 11.sp))
                                Text(text = "${weekInfo.progressPercent}% Complete", style = MaterialTheme.typography.bodySmall.copy(color = PrimaryPink, fontWeight = FontWeight.Bold, fontSize = 11.sp))
                            }
                        }
                    }
                }

                item {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(10.dp)
                    ) {
                        MetricItemCard(label = "Daily Kicks", value = "$kickCount / 10", modifier = Modifier.weight(1f))
                        MetricItemCard(label = "Water Target", value = "$waterGlasses / 10", modifier = Modifier.weight(1f))
                        MetricItemCard(label = "Trimester", value = "Trim ${weekInfo.trimester}", modifier = Modifier.weight(1f))
                    }
                }

                item {
                    Surface(
                        color = SurfaceWhite,
                        shape = RoundedCornerShape(22.dp),
                        border = androidx.compose.foundation.BorderStroke(1.dp, SoftPink),
                        shadowElevation = 2.dp,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Column(modifier = Modifier.padding(18.dp)) {
                            Text(
                                text = "Trimester Timeline",
                                style = MaterialTheme.typography.titleMedium.copy(
                                    fontWeight = FontWeight.Bold,
                                    color = TextPrimary
                                )
                            )
                            Spacer(modifier = Modifier.height(12.dp))

                            TimelineRow(title = "1st Trimester (Weeks 1 - 12)", desc = "Fertilization, organogenesis, early heartbeat", isDone = true)
                            TimelineRow(title = "2nd Trimester (Weeks 13 - 27)", desc = "Auditory senses, rapid growth, baby kicks", isDone = true, isCurrent = true)
                            TimelineRow(title = "3rd Trimester (Weeks 28 - 40)", desc = "Lung maturity, brain growth, labor prep", isDone = false)
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun TimelineRow(
    title: String,
    desc: String,
    isDone: Boolean,
    isCurrent: Boolean = false
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Surface(
            color = if (isCurrent) PrimaryPink else if (isDone) SuccessGreen else SoftPink,
            shape = RoundedCornerShape(8.dp),
            modifier = Modifier.size(16.dp)
        ) {}

        Spacer(modifier = Modifier.width(12.dp))

        Column {
            Text(
                text = title,
                style = MaterialTheme.typography.titleMedium.copy(
                    fontWeight = FontWeight.Bold,
                    color = if (isCurrent) PrimaryPinkDark else TextPrimary,
                    fontSize = 13.sp
                )
            )
            Text(
                text = desc,
                style = MaterialTheme.typography.bodySmall.copy(
                    color = TextSecondary,
                    fontSize = 11.sp
                )
            )
        }
    }
}
