package com.example.ui.screens

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.Stop
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.model.ContractionRecord
import com.example.ui.components.AppHeader
import com.example.ui.theme.*
import kotlinx.coroutines.delay

@Composable
fun ContractionTimerScreen(
    contractions: List<ContractionRecord>,
    onAddContraction: (duration: Int, interval: Int, intensity: String) -> Unit,
    onClearContractions: () -> Unit,
    onBack: () -> Unit,
    modifier: Modifier = Modifier
) {
    var isTiming by remember { mutableStateOf(false) }
    var secondsElapsed by remember { mutableIntStateOf(0) }
    var intensity by remember { mutableStateOf("Moderate") }

    LaunchedEffect(isTiming) {
        if (isTiming) {
            secondsElapsed = 0
            while (isTiming) {
                delay(1000)
                secondsElapsed++
            }
        }
    }

    Surface(
        color = BackgroundLight,
        modifier = modifier.fillMaxSize()
    ) {
        Column(modifier = Modifier.fillMaxSize()) {
            AppHeader(
                title = "Contraction Timer",
                onBackClick = onBack,
                rightIcon = Icons.Default.Delete,
                onRightIconClick = onClearContractions
            )

            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 20.dp),
                verticalArrangement = Arrangement.spacedBy(14.dp),
                contentPadding = PaddingValues(bottom = 32.dp)
            ) {
                // 5-1-1 Clinical Rule Guide
                item {
                    Surface(
                        color = LightRed,
                        shape = RoundedCornerShape(20.dp),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Row(
                            modifier = Modifier.padding(16.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(text = "🏥", fontSize = 28.sp)
                            Spacer(modifier = Modifier.width(12.dp))
                            Column {
                                Text(
                                    text = "The 5-1-1 Rule for Labor",
                                    style = MaterialTheme.typography.titleMedium.copy(
                                        fontWeight = FontWeight.Bold,
                                        color = EmergencyRed,
                                        fontSize = 14.sp
                                    )
                                )
                                Text(
                                    text = "When contractions occur every 5 minutes, last 1 full minute, for 1 hour straight: proceed to maternity triage.",
                                    style = MaterialTheme.typography.bodySmall.copy(color = TextPrimary, fontSize = 11.sp)
                                )
                            }
                        }
                    }
                }

                // Interactive Timer Circle
                item {
                    Surface(
                        color = SurfaceWhite,
                        shape = RoundedCornerShape(28.dp),
                        border = androidx.compose.foundation.BorderStroke(1.dp, SoftPink),
                        shadowElevation = 4.dp,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Column(
                            modifier = Modifier.padding(24.dp),
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Text(
                                text = if (isTiming) "Contraction in Progress..." else "Tap to Start Timing",
                                style = MaterialTheme.typography.bodyMedium.copy(
                                    color = if (isTiming) EmergencyRed else TextSecondary,
                                    fontWeight = FontWeight.Bold
                                )
                            )

                            Spacer(modifier = Modifier.height(16.dp))

                            Box(
                                modifier = Modifier
                                    .size(160.dp)
                                    .clip(CircleShape)
                                    .background(if (isTiming) LightRed else SoftPink)
                                    .clickable {
                                        if (isTiming) {
                                            isTiming = false
                                            onAddContraction(secondsElapsed, 5, intensity)
                                        } else {
                                            isTiming = true
                                        }
                                    }
                                    .testTag("timer_circle_btn"),
                                contentAlignment = Alignment.Center
                            ) {
                                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                                    Icon(
                                        imageVector = if (isTiming) Icons.Default.Stop else Icons.Default.PlayArrow,
                                        contentDescription = null,
                                        tint = if (isTiming) EmergencyRed else PrimaryPink,
                                        modifier = Modifier.size(36.dp)
                                    )
                                    Text(
                                        text = "%02d:%02d".format(secondsElapsed / 60, secondsElapsed % 60),
                                        style = MaterialTheme.typography.headlineLarge.copy(
                                            fontWeight = FontWeight.Bold,
                                            color = if (isTiming) EmergencyRed else PrimaryPinkDark,
                                            fontSize = 32.sp
                                        )
                                    )
                                    Text(
                                        text = if (isTiming) "STOP" else "START",
                                        style = MaterialTheme.typography.labelSmall.copy(
                                            fontWeight = FontWeight.Bold,
                                            color = if (isTiming) EmergencyRed else PrimaryPinkDark
                                        )
                                    )
                                }
                            }
                        }
                    }
                }

                // Contraction History
                item {
                    Text(
                        text = "Contraction History",
                        style = MaterialTheme.typography.titleMedium.copy(
                            fontWeight = FontWeight.Bold,
                            color = TextPrimary
                        )
                    )
                }

                items(contractions, key = { it.id }) { item ->
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
                                    text = "${item.durationSeconds}s Duration",
                                    style = MaterialTheme.typography.titleMedium.copy(
                                        fontWeight = FontWeight.Bold,
                                        color = TextPrimary,
                                        fontSize = 14.sp
                                    )
                                )
                                Text(
                                    text = "Started: ${item.startTime} · Intensity: ${item.intensity}",
                                    style = MaterialTheme.typography.bodySmall.copy(color = TextSecondary, fontSize = 11.sp)
                                )
                            }

                            Surface(
                                color = SoftPink,
                                shape = RoundedCornerShape(12.dp)
                            ) {
                                Text(
                                    text = "Every ${item.intervalMinutes}m",
                                    style = MaterialTheme.typography.labelSmall.copy(
                                        fontWeight = FontWeight.Bold,
                                        color = PrimaryPinkDark
                                    ),
                                    modifier = Modifier.padding(horizontal = 10.dp, vertical = 4.dp)
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}
