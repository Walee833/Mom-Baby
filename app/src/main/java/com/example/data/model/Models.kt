package com.example.data.model

data class UserProfile(
    val name: String = "Blessing Okon",
    val email: String = "blessing@example.com",
    val currentWeek: Int = 24,
    val currentDay: Int = 3,
    val dueDate: String = "Nov 15, 2024",
    val babyNickname: String = "Little Peanut",
    val doctorName: String = "Dr. Adaeze Johnson",
    val hospitalName: String = "General Hospital Maternity Clinic",
    val partnerName: String = "Emeka Okon",
    val partnerPhone: String = "+234 801 234 5678"
)

data class Appointment(
    val id: String,
    val title: String,
    val hospital: String,
    val doctor: String,
    val month: String,
    val day: String,
    val year: String,
    val time: String,
    val isPast: Boolean = false,
    val notes: String = ""
)

data class VaccineItem(
    val id: String,
    val title: String,
    val recommendedTime: String,
    val trimester: Int,
    val doses: String,
    val description: String,
    val isCompleted: Boolean = false
)

data class MedicationItem(
    val id: String,
    val name: String,
    val dosage: String,
    val time: String,
    val frequency: String,
    val isTaken: Boolean = false
)

data class BabyGrowthRecord(
    val id: String,
    val period: String,
    val weightKg: Float,
    val heightCm: Float,
    val headCircumferenceCm: Float,
    val bmi: Float,
    val recordedDate: String
)

data class BabyMilestone(
    val id: String,
    val title: String,
    val ageRange: String,
    val category: String, // "Motor", "Sensory", "Social", "Language"
    val isAchieved: Boolean = false,
    val description: String = ""
)

data class Article(
    val id: String,
    val title: String,
    val readTime: String,
    val category: String,
    val summary: String,
    val content: String,
    val isBookmarked: Boolean = false
)

data class Recipe(
    val id: String,
    val title: String,
    val prepTime: String,
    val calories: String,
    val category: String,
    val benefits: String,
    val ingredients: List<String>,
    val instructions: List<String>
)

data class EmergencyContact(
    val id: String,
    val name: String,
    val relationship: String,
    val phoneNumber: String,
    val isQuickCall: Boolean = true
)

data class ChatMessage(
    val id: String,
    val senderName: String,
    val text: String,
    val timestamp: String,
    val isFromUser: Boolean
)

data class NotificationItem(
    val id: String,
    val title: String,
    val message: String,
    val timeAgo: String,
    val isRead: Boolean = false
)

data class CalendarDayEvent(
    val id: String,
    val day: Int,
    val month: Int,
    val year: Int,
    val title: String,
    val time: String,
    val category: String,
    val colorHex: Long
)

data class ContractionRecord(
    val id: String,
    val startTime: String,
    val durationSeconds: Int,
    val intervalMinutes: Int,
    val intensity: String = "Moderate"
)

data class HospitalBagItem(
    val id: String,
    val category: String,
    val name: String,
    val isPacked: Boolean = false,
    val quantityNote: String = "1x"
)
