package com.example.ui.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.model.Appointment
import com.example.ui.components.AppHeader
import com.example.ui.components.SegmentedTabBar
import com.example.ui.theme.*

@Composable
fun AppointmentsScreen(
    appointments: List<Appointment>,
    onAddAppointmentClick: () -> Unit,
    onDeleteAppointment: (String) -> Unit,
    onBack: () -> Unit,
    modifier: Modifier = Modifier
) {
    var selectedTab by remember { mutableIntStateOf(0) }
    val tabs = listOf("Upcoming", "Past Visits")

    val filteredAppointments = if (selectedTab == 0) {
        appointments.filter { !it.isPast }
    } else {
        appointments.filter { it.isPast }
    }

    Surface(
        color = BackgroundLight,
        modifier = modifier.fillMaxSize()
    ) {
        Column(modifier = Modifier.fillMaxSize()) {
            AppHeader(
                title = "Appointments",
                onBackClick = onBack,
                rightIcon = Icons.Default.Add,
                onRightIconClick = onAddAppointmentClick
            )

            Spacer(modifier = Modifier.height(8.dp))

            SegmentedTabBar(
                tabs = tabs,
                selectedIndex = selectedTab,
                onTabSelected = { selectedTab = it },
                modifier = Modifier.testTag("appointments_tabs")
            )

            Spacer(modifier = Modifier.height(14.dp))

            if (filteredAppointments.isEmpty()) {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(24.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Text("📅", fontSize = 48.sp)
                        Spacer(modifier = Modifier.height(12.dp))
                        Text(
                            text = if (selectedTab == 0) "No upcoming appointments scheduled" else "No past appointments recorded",
                            style = MaterialTheme.typography.bodyMedium.copy(color = TextSecondary)
                        )
                        Spacer(modifier = Modifier.height(14.dp))
                        Button(
                            onClick = onAddAppointmentClick,
                            colors = ButtonDefaults.buttonColors(containerColor = PrimaryPink),
                            shape = RoundedCornerShape(20.dp)
                        ) {
                            Text("+ Schedule New Visit")
                        }
                    }
                }
            } else {
                LazyColumn(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(horizontal = 20.dp),
                    verticalArrangement = Arrangement.spacedBy(14.dp),
                    contentPadding = PaddingValues(bottom = 32.dp)
                ) {
                    items(filteredAppointments, key = { it.id }) { item ->
                        AppointmentCard(
                            appointment = item,
                            onDelete = { onDeleteAppointment(item.id) }
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun AppointmentCard(
    appointment: Appointment,
    onDelete: () -> Unit
) {
    Surface(
        color = SurfaceWhite,
        shape = RoundedCornerShape(22.dp),
        border = androidx.compose.foundation.BorderStroke(1.dp, SoftPink),
        shadowElevation = 2.dp,
        modifier = Modifier.fillMaxWidth().testTag("appointment_card_${appointment.id}")
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Surface(
                    color = SoftPink,
                    shape = RoundedCornerShape(14.dp),
                    modifier = Modifier.size(52.dp)
                ) {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center
                    ) {
                        Text(
                            text = appointment.month,
                            style = MaterialTheme.typography.labelSmall.copy(
                                fontWeight = FontWeight.Bold,
                                color = PrimaryPink,
                                fontSize = 10.sp
                            )
                        )
                        Text(
                            text = appointment.day,
                            style = MaterialTheme.typography.titleMedium.copy(
                                fontWeight = FontWeight.Bold,
                                color = PrimaryPinkDark
                            )
                        )
                    }
                }

                Spacer(modifier = Modifier.width(14.dp))

                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        text = appointment.title,
                        style = MaterialTheme.typography.titleMedium.copy(
                            fontWeight = FontWeight.Bold,
                            color = TextPrimary,
                            fontSize = 15.sp
                        )
                    )
                    Text(
                        text = "${appointment.hospital} · ${appointment.time}",
                        style = MaterialTheme.typography.bodySmall.copy(
                            color = TextSecondary,
                            fontSize = 12.sp
                        )
                    )
                    Text(
                        text = appointment.doctor,
                        style = MaterialTheme.typography.bodySmall.copy(
                            color = PrimaryPink,
                            fontWeight = FontWeight.Medium,
                            fontSize = 12.sp
                        )
                    )
                }

                IconButton(onClick = onDelete) {
                    Icon(
                        imageVector = Icons.Default.DeleteOutline,
                        contentDescription = "Delete",
                        tint = TextSecondary,
                        modifier = Modifier.size(20.dp)
                    )
                }
            }

            if (appointment.notes.isNotBlank()) {
                Spacer(modifier = Modifier.height(10.dp))
                Surface(
                    color = BackgroundLight,
                    shape = RoundedCornerShape(12.dp),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(
                        text = "Notes: ${appointment.notes}",
                        style = MaterialTheme.typography.bodySmall.copy(
                            color = TextSecondary,
                            fontSize = 11.sp
                        ),
                        modifier = Modifier.padding(10.dp)
                    )
                }
            }
        }
    }
}
