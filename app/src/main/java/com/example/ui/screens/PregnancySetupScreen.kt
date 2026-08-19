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
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ui.components.AppHeader
import com.example.ui.components.PrimaryPinkButton
import com.example.ui.theme.*

@Composable
fun PregnancySetupScreen(
    onSetupComplete: (dueDate: String, babyNickname: String, doctor: String, hospital: String) -> Unit,
    onBack: () -> Unit,
    modifier: Modifier = Modifier
) {
    var step by remember { mutableIntStateOf(1) }
    var babyNickname by remember { mutableStateOf("Little Peanut") }
    var dueDate by remember { mutableStateOf("Nov 15, 2024") }
    var doctorName by remember { mutableStateOf("Dr. Adaeze Johnson") }
    var hospitalName by remember { mutableStateOf("General Hospital, Lagos") }
    var isFirstTimeMom by remember { mutableStateOf(true) }

    Surface(
        color = BackgroundLight,
        modifier = modifier.fillMaxSize()
    ) {
        Column(modifier = Modifier.fillMaxSize()) {
            AppHeader(
                title = "Pregnancy Setup",
                onBackClick = {
                    if (step > 1) step-- else onBack()
                }
            )

            // Step Progress Indicator
            Column(modifier = Modifier.padding(horizontal = 24.dp, vertical = 12.dp)) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        text = "Step $step of 3",
                        style = MaterialTheme.typography.labelSmall.copy(
                            color = PrimaryPinkDark,
                            fontWeight = FontWeight.Bold
                        )
                    )
                    Text(
                        text = when (step) {
                            1 -> "Due Date"
                            2 -> "Baby Details"
                            else -> "Care Team"
                        },
                        style = MaterialTheme.typography.labelSmall.copy(
                            color = TextSecondary,
                            fontWeight = FontWeight.Medium
                        )
                    )
                }

                Spacer(modifier = Modifier.height(8.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(6.dp)
                ) {
                    (1..3).forEach { i ->
                        Box(
                            modifier = Modifier
                                .weight(1f)
                                .height(6.dp)
                                .clip(RoundedCornerShape(3.dp))
                                .background(if (i <= step) PrimaryPink else BorderSoft)
                        )
                    }
                }
            }

            LazyColumn(
                modifier = Modifier
                    .weight(1f)
                    .padding(horizontal = 24.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp),
                contentPadding = PaddingValues(top = 12.dp, bottom = 24.dp)
            ) {
                when (step) {
                    1 -> {
                        item {
                            Text(
                                text = "When is your baby expected?",
                                style = MaterialTheme.typography.titleLarge.copy(
                                    fontWeight = FontWeight.Bold,
                                    color = TextPrimary
                                )
                            )
                            Spacer(modifier = Modifier.height(4.dp))
                            Text(
                                text = "We will calculate your current gestational week, developmental milestones, and appointment schedules based on your estimated due date.",
                                style = MaterialTheme.typography.bodyMedium.copy(color = TextSecondary)
                            )
                        }

                        item {
                            OutlinedTextField(
                                value = dueDate,
                                onValueChange = { dueDate = it },
                                label = { Text("Estimated Due Date") },
                                placeholder = { Text("e.g. Nov 15, 2024") },
                                leadingIcon = { Icon(Icons.Default.CalendarToday, contentDescription = null, tint = PrimaryPink) },
                                shape = RoundedCornerShape(16.dp),
                                colors = OutlinedTextFieldDefaults.colors(
                                    focusedContainerColor = SurfaceWhite,
                                    unfocusedContainerColor = SurfaceWhite,
                                    focusedBorderColor = PrimaryPink,
                                    unfocusedBorderColor = SoftPink
                                ),
                                modifier = Modifier.fillMaxWidth().testTag("setup_due_date_input")
                            )
                        }

                        item {
                            Surface(
                                color = SoftPink.copy(alpha = 0.5f),
                                shape = RoundedCornerShape(20.dp),
                                modifier = Modifier.fillMaxWidth()
                            ) {
                                Row(
                                    modifier = Modifier.padding(16.dp),
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Text(text = "💡", fontSize = 24.sp)
                                    Spacer(modifier = Modifier.width(12.dp))
                                    Text(
                                        text = "Don't know the exact date? You can estimate 40 weeks (280 days) from the first day of your last period.",
                                        style = MaterialTheme.typography.bodySmall.copy(color = PrimaryPinkDark)
                                    )
                                }
                            }
                        }
                    }

                    2 -> {
                        item {
                            Text(
                                text = "Personalize your journey",
                                style = MaterialTheme.typography.titleLarge.copy(
                                    fontWeight = FontWeight.Bold,
                                    color = TextPrimary
                                )
                            )
                            Spacer(modifier = Modifier.height(4.dp))
                            Text(
                                text = "Give your little one a sweet temporary nickname to make your daily updates special.",
                                style = MaterialTheme.typography.bodyMedium.copy(color = TextSecondary)
                            )
                        }

                        item {
                            OutlinedTextField(
                                value = babyNickname,
                                onValueChange = { babyNickname = it },
                                label = { Text("Baby Nickname") },
                                placeholder = { Text("e.g. Little Peanut, Sunshine, Bean") },
                                leadingIcon = { Icon(Icons.Default.Favorite, contentDescription = null, tint = PrimaryPink) },
                                shape = RoundedCornerShape(16.dp),
                                colors = OutlinedTextFieldDefaults.colors(
                                    focusedContainerColor = SurfaceWhite,
                                    unfocusedContainerColor = SurfaceWhite,
                                    focusedBorderColor = PrimaryPink,
                                    unfocusedBorderColor = SoftPink
                                ),
                                modifier = Modifier.fillMaxWidth().testTag("setup_baby_nickname_input")
                            )
                        }

                        item {
                            Surface(
                                color = SurfaceWhite,
                                shape = RoundedCornerShape(20.dp),
                                border = androidx.compose.foundation.BorderStroke(1.dp, SoftPink),
                                modifier = Modifier.fillMaxWidth()
                            ) {
                                Row(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .clickable { isFirstTimeMom = !isFirstTimeMom }
                                        .padding(16.dp),
                                    verticalAlignment = Alignment.CenterVertically,
                                    horizontalArrangement = Arrangement.SpaceBetween
                                ) {
                                    Column(modifier = Modifier.weight(1f)) {
                                        Text(
                                            text = "Is this your first pregnancy?",
                                            style = MaterialTheme.typography.titleMedium.copy(
                                                fontWeight = FontWeight.SemiBold,
                                                color = TextPrimary,
                                                fontSize = 14.sp
                                            )
                                        )
                                        Text(
                                            text = "We will tailor beginner pregnancy & newborn guides for you.",
                                            style = MaterialTheme.typography.bodySmall.copy(color = TextSecondary, fontSize = 12.sp)
                                        )
                                    }

                                    Switch(
                                        checked = isFirstTimeMom,
                                        onCheckedChange = { isFirstTimeMom = it },
                                        colors = SwitchDefaults.colors(
                                            checkedThumbColor = Color.White,
                                            checkedTrackColor = PrimaryPink
                                        )
                                    )
                                }
                            }
                        }
                    }

                    3 -> {
                        item {
                            Text(
                                text = "Your Healthcare Providers",
                                style = MaterialTheme.typography.titleLarge.copy(
                                    fontWeight = FontWeight.Bold,
                                    color = TextPrimary
                                )
                            )
                            Spacer(modifier = Modifier.height(4.dp))
                            Text(
                                text = "Add your designated clinic and obstetrician for seamless appointment and emergency sync.",
                                style = MaterialTheme.typography.bodyMedium.copy(color = TextSecondary)
                            )
                        }

                        item {
                            OutlinedTextField(
                                value = doctorName,
                                onValueChange = { doctorName = it },
                                label = { Text("Obstetrician / Doctor Name") },
                                placeholder = { Text("e.g. Dr. Adaeze Johnson") },
                                leadingIcon = { Icon(Icons.Default.MedicalServices, contentDescription = null, tint = PrimaryPink) },
                                shape = RoundedCornerShape(16.dp),
                                colors = OutlinedTextFieldDefaults.colors(
                                    focusedContainerColor = SurfaceWhite,
                                    unfocusedContainerColor = SurfaceWhite,
                                    focusedBorderColor = PrimaryPink,
                                    unfocusedBorderColor = SoftPink
                                ),
                                modifier = Modifier.fillMaxWidth().testTag("setup_doctor_input")
                            )
                        }

                        item {
                            OutlinedTextField(
                                value = hospitalName,
                                onValueChange = { hospitalName = it },
                                label = { Text("Hospital / Maternity Clinic") },
                                placeholder = { Text("e.g. General Hospital, Lagos") },
                                leadingIcon = { Icon(Icons.Default.LocalHospital, contentDescription = null, tint = PrimaryPink) },
                                shape = RoundedCornerShape(16.dp),
                                colors = OutlinedTextFieldDefaults.colors(
                                    focusedContainerColor = SurfaceWhite,
                                    unfocusedContainerColor = SurfaceWhite,
                                    focusedBorderColor = PrimaryPink,
                                    unfocusedBorderColor = SoftPink
                                ),
                                modifier = Modifier.fillMaxWidth().testTag("setup_hospital_input")
                            )
                        }
                    }
                }
            }

            // Bottom Button
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 24.dp, vertical = 16.dp)
            ) {
                PrimaryPinkButton(
                    text = if (step < 3) "Continue to Step ${step + 1}" else "Finish Setup & Launch Dashboard",
                    onClick = {
                        if (step < 3) {
                            step++
                        } else {
                            onSetupComplete(dueDate, babyNickname, doctorName, hospitalName)
                        }
                    },
                    testTag = "setup_continue_btn"
                )
            }
        }
    }
}
