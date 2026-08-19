package com.example.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.data.model.UserProfile
import com.example.ui.theme.AppTheme

@Composable
fun AddAppointmentDialog(
    onDismiss: () -> Unit,
    onConfirm: (title: String, hospital: String, doctor: String, month: String, day: String, year: String, time: String, notes: String) -> Unit
) {
    val colors = AppTheme.colors
    var title by remember { mutableStateOf("") }
    var hospital by remember { mutableStateOf("General Hospital Maternity Clinic") }
    var doctor by remember { mutableStateOf("Dr. Adaeze Johnson") }
    var month by remember { mutableStateOf("NOV") }
    var day by remember { mutableStateOf("15") }
    var year by remember { mutableStateOf("2024") }
    var time by remember { mutableStateOf("10:00 AM") }
    var notes by remember { mutableStateOf("") }

    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text("Schedule Prenatal Visit", fontWeight = FontWeight.Bold, color = colors.textPrimary) },
        text = {
            Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                OutlinedTextField(value = title, onValueChange = { title = it }, label = { Text("Visit Purpose") }, placeholder = { Text("e.g. 28-Week Ultrasound") }, modifier = Modifier.fillMaxWidth())
                OutlinedTextField(value = doctor, onValueChange = { doctor = it }, label = { Text("Doctor / Specialist") }, modifier = Modifier.fillMaxWidth())
                Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    OutlinedTextField(value = month, onValueChange = { month = it }, label = { Text("Month") }, modifier = Modifier.weight(1f))
                    OutlinedTextField(value = day, onValueChange = { day = it }, label = { Text("Day") }, modifier = Modifier.weight(1f))
                    OutlinedTextField(value = time, onValueChange = { time = it }, label = { Text("Time") }, modifier = Modifier.weight(1.5f))
                }
                OutlinedTextField(value = notes, onValueChange = { notes = it }, label = { Text("Preparation Notes") }, modifier = Modifier.fillMaxWidth())
            }
        },
        confirmButton = {
            Button(
                onClick = {
                    if (title.isNotBlank()) {
                        onConfirm(title, hospital, doctor, month, day, year, time, notes)
                    }
                },
                colors = ButtonDefaults.buttonColors(containerColor = colors.primary)
            ) {
                Text("Save Visit", color = if (colors.isDark) colors.background else androidx.compose.ui.graphics.Color.White)
            }
        },
        dismissButton = { TextButton(onClick = onDismiss) { Text("Cancel", color = colors.textSecondary) } }
    )
}

@Composable
fun AddMedicationDialog(
    onDismiss: () -> Unit,
    onConfirm: (name: String, dosage: String, time: String, frequency: String) -> Unit
) {
    val colors = AppTheme.colors
    var name by remember { mutableStateOf("") }
    var dosage by remember { mutableStateOf("") }
    var time by remember { mutableStateOf("08:00 AM") }
    var frequency by remember { mutableStateOf("Daily with meal") }

    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text("Add Medication / Vitamin", fontWeight = FontWeight.Bold, color = colors.textPrimary) },
        text = {
            Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                OutlinedTextField(value = name, onValueChange = { name = it }, label = { Text("Medication Name") }, placeholder = { Text("e.g. Folic Acid") }, modifier = Modifier.fillMaxWidth())
                OutlinedTextField(value = dosage, onValueChange = { dosage = it }, label = { Text("Dosage") }, placeholder = { Text("e.g. 400 mcg") }, modifier = Modifier.fillMaxWidth())
                OutlinedTextField(value = time, onValueChange = { time = it }, label = { Text("Time of Day") }, modifier = Modifier.fillMaxWidth())
                OutlinedTextField(value = frequency, onValueChange = { frequency = it }, label = { Text("Frequency") }, modifier = Modifier.fillMaxWidth())
            }
        },
        confirmButton = {
            Button(
                onClick = {
                    if (name.isNotBlank()) {
                        onConfirm(name, dosage, time, frequency)
                    }
                },
                colors = ButtonDefaults.buttonColors(containerColor = colors.primary)
            ) {
                Text("Add", color = if (colors.isDark) colors.background else androidx.compose.ui.graphics.Color.White)
            }
        },
        dismissButton = { TextButton(onClick = onDismiss) { Text("Cancel", color = colors.textSecondary) } }
    )
}

@Composable
fun AddEmergencyContactDialog(
    onDismiss: () -> Unit,
    onConfirm: (name: String, relation: String, phone: String) -> Unit
) {
    val colors = AppTheme.colors
    var name by remember { mutableStateOf("") }
    var relation by remember { mutableStateOf("Emergency Support") }
    var phone by remember { mutableStateOf("") }

    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text("Add Emergency Contact", fontWeight = FontWeight.Bold, color = colors.textPrimary) },
        text = {
            Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                OutlinedTextField(value = name, onValueChange = { name = it }, label = { Text("Contact Name") }, modifier = Modifier.fillMaxWidth())
                OutlinedTextField(value = relation, onValueChange = { relation = it }, label = { Text("Relationship / Role") }, modifier = Modifier.fillMaxWidth())
                OutlinedTextField(value = phone, onValueChange = { phone = it }, label = { Text("Phone Number") }, modifier = Modifier.fillMaxWidth())
            }
        },
        confirmButton = {
            Button(
                onClick = {
                    if (name.isNotBlank() && phone.isNotBlank()) {
                        onConfirm(name, relation, phone)
                    }
                },
                colors = ButtonDefaults.buttonColors(containerColor = colors.primary)
            ) {
                Text("Save Contact", color = if (colors.isDark) colors.background else androidx.compose.ui.graphics.Color.White)
            }
        },
        dismissButton = { TextButton(onClick = onDismiss) { Text("Cancel", color = colors.textSecondary) } }
    )
}

@Composable
fun AddGrowthDialog(
    onDismiss: () -> Unit,
    onConfirm: (period: String, weight: Float, height: Float, headCircumference: Float, bmi: Float, date: String) -> Unit
) {
    val colors = AppTheme.colors
    var period by remember { mutableStateOf("Month 1") }
    var weightStr by remember { mutableStateOf("4.2") }
    var heightStr by remember { mutableStateOf("54.0") }
    var headStr by remember { mutableStateOf("37.0") }

    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text("Log Baby Growth Record", fontWeight = FontWeight.Bold, color = colors.textPrimary) },
        text = {
            Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                OutlinedTextField(value = period, onValueChange = { period = it }, label = { Text("Age Period (e.g. Month 1)") }, modifier = Modifier.fillMaxWidth())
                OutlinedTextField(value = weightStr, onValueChange = { weightStr = it }, label = { Text("Weight (kg)") }, modifier = Modifier.fillMaxWidth())
                OutlinedTextField(value = heightStr, onValueChange = { heightStr = it }, label = { Text("Length / Height (cm)") }, modifier = Modifier.fillMaxWidth())
                OutlinedTextField(value = headStr, onValueChange = { headStr = it }, label = { Text("Head Circumference (cm)") }, modifier = Modifier.fillMaxWidth())
            }
        },
        confirmButton = {
            Button(
                onClick = {
                    val w = weightStr.toFloatOrNull() ?: 4.0f
                    val h = heightStr.toFloatOrNull() ?: 50.0f
                    val hd = headStr.toFloatOrNull() ?: 35.0f
                    val heightInMeters = h / 100f
                    val bmi = if (heightInMeters > 0f) w / (heightInMeters * heightInMeters) else 15f
                    onConfirm(period, w, h, hd, bmi, "Today")
                },
                colors = ButtonDefaults.buttonColors(containerColor = colors.primary)
            ) {
                Text("Log Growth", color = if (colors.isDark) colors.background else androidx.compose.ui.graphics.Color.White)
            }
        },
        dismissButton = { TextButton(onClick = onDismiss) { Text("Cancel", color = colors.textSecondary) } }
    )
}

@Composable
fun EditProfileDialog(
    profile: UserProfile,
    onDismiss: () -> Unit,
    onConfirm: (name: String, email: String, dueDate: String, nickname: String, doctor: String, hospital: String, partner: String, partnerPhone: String) -> Unit
) {
    val colors = AppTheme.colors
    var name by remember { mutableStateOf(profile.name) }
    var email by remember { mutableStateOf(profile.email) }
    var dueDate by remember { mutableStateOf(profile.dueDate) }
    var nickname by remember { mutableStateOf(profile.babyNickname) }
    var doctor by remember { mutableStateOf(profile.doctorName) }
    var hospital by remember { mutableStateOf(profile.hospitalName) }
    var partner by remember { mutableStateOf(profile.partnerName) }
    var partnerPhone by remember { mutableStateOf(profile.partnerPhone) }

    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text("Edit Mom & Baby Profile", fontWeight = FontWeight.Bold, color = colors.textPrimary) },
        text = {
            Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                OutlinedTextField(value = name, onValueChange = { name = it }, label = { Text("Mother's Full Name") }, modifier = Modifier.fillMaxWidth())
                OutlinedTextField(value = dueDate, onValueChange = { dueDate = it }, label = { Text("Due Date") }, modifier = Modifier.fillMaxWidth())
                OutlinedTextField(value = nickname, onValueChange = { nickname = it }, label = { Text("Baby Nickname") }, modifier = Modifier.fillMaxWidth())
                OutlinedTextField(value = doctor, onValueChange = { doctor = it }, label = { Text("Doctor Name") }, modifier = Modifier.fillMaxWidth())
                OutlinedTextField(value = hospital, onValueChange = { hospital = it }, label = { Text("Hospital") }, modifier = Modifier.fillMaxWidth())
            }
        },
        confirmButton = {
            Button(
                onClick = {
                    onConfirm(name, email, dueDate, nickname, doctor, hospital, partner, partnerPhone)
                },
                colors = ButtonDefaults.buttonColors(containerColor = colors.primary)
            ) {
                Text("Update", color = if (colors.isDark) colors.background else androidx.compose.ui.graphics.Color.White)
            }
        },
        dismissButton = { TextButton(onClick = onDismiss) { Text("Cancel", color = colors.textSecondary) } }
    )
}
