package com.example.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.data.model.*
import com.example.data.repository.MomBabyRepository
import kotlinx.coroutines.flow.*

enum class Screen {
    WELCOME,
    SIGN_UP,
    LOGIN,
    FORGOT_PASSWORD,
    PREGNANCY_SETUP,
    HOME,
    PREGNANCY_TRACKER,
    APPOINTMENTS,
    VACCINES,
    BABY_GROWTH,
    NUTRITION,
    HEALTH_EDUCATION,
    MEDICATIONS,
    EMERGENCY_CONTACTS,
    CALENDAR,
    PROGRESS,
    MESSAGES,
    PROFILE,
    ARTICLE_DETAIL,
    HOSPITAL_BAG,
    CONTRACTION_TIMER,
    SETTINGS,
    HELP_SUPPORT
}

enum class BottomTab {
    HOME, CALENDAR, PROGRESS, MESSAGES, PROFILE
}

enum class ThemeMode {
    SYSTEM, LIGHT, DARK
}

data class PregnancyWeekInfo(
    val week: Int,
    val fruitOrVeggie: String,
    val emoji: String,
    val weight: String,
    val length: String,
    val trimester: Int,
    val daysRemaining: Int,
    val progressPercent: Int,
    val description: String,
    val highlights: List<String>
)

class MomBabyViewModel(
    private val repository: MomBabyRepository = MomBabyRepository()
) : ViewModel() {

    private val _currentScreen = MutableStateFlow(Screen.HOME)
    val currentScreen: StateFlow<Screen> = _currentScreen.asStateFlow()

    private val _activeBottomTab = MutableStateFlow(BottomTab.HOME)
    val activeBottomTab: StateFlow<BottomTab> = _activeBottomTab.asStateFlow()

    private val _themeMode = MutableStateFlow(ThemeMode.SYSTEM)
    val themeMode: StateFlow<ThemeMode> = _themeMode.asStateFlow()

    fun setThemeMode(mode: ThemeMode) {
        _themeMode.value = mode
    }

    private val _selectedArticle = MutableStateFlow<Article?>(null)
    val selectedArticle: StateFlow<Article?> = _selectedArticle.asStateFlow()

    private val _showAddAppointmentDialog = MutableStateFlow(false)
    val showAddAppointmentDialog: StateFlow<Boolean> = _showAddAppointmentDialog.asStateFlow()

    private val _showAddMedicationDialog = MutableStateFlow(false)
    val showAddMedicationDialog: StateFlow<Boolean> = _showAddMedicationDialog.asStateFlow()

    private val _showAddContactDialog = MutableStateFlow(false)
    val showAddContactDialog: StateFlow<Boolean> = _showAddContactDialog.asStateFlow()

    private val _showAddGrowthDialog = MutableStateFlow(false)
    val showAddGrowthDialog: StateFlow<Boolean> = _showAddGrowthDialog.asStateFlow()

    private val _showEditProfileDialog = MutableStateFlow(false)
    val showEditProfileDialog: StateFlow<Boolean> = _showEditProfileDialog.asStateFlow()

    // Repository flows
    val userProfile: StateFlow<UserProfile> = repository.userProfile
    val currentWeek: StateFlow<Int> = repository.currentWeek
    val kickCount: StateFlow<Int> = repository.kickCount
    val waterGlasses: StateFlow<Int> = repository.waterGlasses
    val appointments: StateFlow<List<Appointment>> = repository.appointments
    val vaccines: StateFlow<List<VaccineItem>> = repository.vaccines
    val medications: StateFlow<List<MedicationItem>> = repository.medications
    val growthRecords: StateFlow<List<BabyGrowthRecord>> = repository.growthRecords
    val babyMilestones: StateFlow<List<BabyMilestone>> = repository.babyMilestones
    val emergencyContacts: StateFlow<List<EmergencyContact>> = repository.emergencyContacts
    val articles: StateFlow<List<Article>> = repository.articles
    val recipes: StateFlow<List<Recipe>> = repository.recipes
    val chatMessages: StateFlow<List<ChatMessage>> = repository.chatMessages
    val notifications: StateFlow<List<NotificationItem>> = repository.notifications
    val calendarEvents: StateFlow<List<CalendarDayEvent>> = repository.calendarEvents
    val contractions: StateFlow<List<ContractionRecord>> = repository.contractions
    val hospitalBagItems: StateFlow<List<HospitalBagItem>> = repository.hospitalBagItems

    val currentWeekInfo: StateFlow<PregnancyWeekInfo> = currentWeek.map { week ->
        getWeekInfo(week)
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), getWeekInfo(24))

    val unreadNotificationsCount: StateFlow<Int> = notifications.map { list ->
        list.count { !it.isRead }
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), 2)

    fun navigateTo(screen: Screen) {
        _currentScreen.value = screen
        when (screen) {
            Screen.HOME -> _activeBottomTab.value = BottomTab.HOME
            Screen.CALENDAR -> _activeBottomTab.value = BottomTab.CALENDAR
            Screen.PROGRESS -> _activeBottomTab.value = BottomTab.PROGRESS
            Screen.MESSAGES -> _activeBottomTab.value = BottomTab.MESSAGES
            Screen.PROFILE -> _activeBottomTab.value = BottomTab.PROFILE
            else -> {}
        }
    }

    fun selectBottomTab(tab: BottomTab) {
        _activeBottomTab.value = tab
        when (tab) {
            BottomTab.HOME -> _currentScreen.value = Screen.HOME
            BottomTab.CALENDAR -> _currentScreen.value = Screen.CALENDAR
            BottomTab.PROGRESS -> _currentScreen.value = Screen.PROGRESS
            BottomTab.MESSAGES -> _currentScreen.value = Screen.MESSAGES
            BottomTab.PROFILE -> _currentScreen.value = Screen.PROFILE
        }
    }

    fun openArticle(article: Article) {
        _selectedArticle.value = article
        _currentScreen.value = Screen.ARTICLE_DETAIL
    }

    fun markAllNotificationsRead() = repository.markAllNotificationsRead()

    fun setShowAddAppointment(show: Boolean) { _showAddAppointmentDialog.value = show }
    fun setShowAddMedication(show: Boolean) { _showAddMedicationDialog.value = show }
    fun setShowAddContact(show: Boolean) { _showAddContactDialog.value = show }
    fun setShowAddGrowth(show: Boolean) { _showAddGrowthDialog.value = show }
    fun setShowEditProfile(show: Boolean) { _showEditProfileDialog.value = show }

    fun setWeek(week: Int) = repository.setPregnancyWeek(week)
    fun incrementKicks() = repository.incrementKicks()
    fun incrementKick() = repository.incrementKicks()
    fun resetKicks() = repository.resetKicks()
    fun incrementWater() = repository.incrementWater()
    fun decrementWater() = repository.decrementWater()
    fun toggleMedicationTaken(id: String) = repository.toggleMedicationTaken(id)
    fun addMedication(name: String, dosage: String, time: String, frequency: String) {
        repository.addMedication(name, dosage, time, frequency)
        _showAddMedicationDialog.value = false
    }
    fun deleteMedication(id: String) = repository.deleteMedication(id)
    fun toggleVaccine(id: String) = repository.toggleVaccineCompleted(id)
    fun addAppointment(title: String, hospital: String, doctor: String, month: String, day: String, year: String, time: String, notes: String) {
        repository.addAppointment(title, hospital, doctor, month, day, year, time, notes)
        _showAddAppointmentDialog.value = false
    }
    fun deleteAppointment(id: String) = repository.deleteAppointment(id)
    fun addEmergencyContact(name: String, relation: String, phone: String) {
        repository.addEmergencyContact(name, relation, phone)
        _showAddContactDialog.value = false
    }
    fun deleteEmergencyContact(id: String) = repository.deleteEmergencyContact(id)
    fun deleteContact(id: String) = repository.deleteEmergencyContact(id)
    fun addGrowthRecord(period: String, weight: Float, height: Float, head: Float, bmi: Float, date: String) {
        repository.addGrowthRecord(period, weight, height, head, bmi, date)
        _showAddGrowthDialog.value = false
    }
    fun toggleMilestone(id: String) = repository.toggleMilestone(id)
    fun toggleBookmarkArticle(id: String) = repository.toggleBookmarkArticle(id)
    fun addContraction(durationSeconds: Int, intervalMinutes: Int, intensity: String) = repository.addContraction(durationSeconds, intervalMinutes, intensity)
    fun clearContractions() = repository.clearContractions()
    fun toggleHospitalBagItem(id: String) = repository.toggleHospitalBagItem(id)
    fun addHospitalBagItem(name: String, category: String, quantity: String) = repository.addHospitalBagItem(name, category, quantity)
    fun sendChatMessage(text: String) = repository.sendChatMessage(text)
    fun updateProfile(name: String, email: String, dueDate: String, nickname: String, doctor: String, hospital: String, partner: String, partnerPhone: String) {
        repository.updateProfile(name, email, dueDate, nickname, doctor, hospital, partner, partnerPhone)
        _showEditProfileDialog.value = false
    }
    fun updateUserProfile(name: String, email: String, dueDate: String, nickname: String, doctor: String, hospital: String, partner: String, partnerPhone: String) {
        updateProfile(name, email, dueDate, nickname, doctor, hospital, partner, partnerPhone)
    }

    fun getWeekInfo(week: Int): PregnancyWeekInfo {
        val totalDays = 280
        val daysPassed = week * 7 + 3
        val daysRemaining = (totalDays - daysPassed).coerceAtLeast(0)
        val progressPercent = ((daysPassed.toFloat() / totalDays.toFloat()) * 100).toInt().coerceIn(1, 100)
        val trimester = when {
            week < 13 -> 1
            week < 28 -> 2
            else -> 3
        }

        return when (week) {
            in 4..8 -> PregnancyWeekInfo(
                week = week,
                fruitOrVeggie = "Raspberry",
                emoji = "🫐",
                weight = "1.1 g",
                length = "1.6 cm",
                trimester = 1,
                daysRemaining = daysRemaining,
                progressPercent = progressPercent,
                description = "Baby's neural tube is closing and early heart beats can be heard on ultrasound.",
                highlights = listOf("Heart is beating steadily", "Tiny arm and leg buds forming", "Facial features beginning to take shape")
            )
            in 9..14 -> PregnancyWeekInfo(
                week = week,
                fruitOrVeggie = "Lemon",
                emoji = "🍋",
                weight = "45 g",
                length = "8.7 cm",
                trimester = 1,
                daysRemaining = daysRemaining,
                progressPercent = progressPercent,
                description = "Baby is opening and closing tiny fists. Reflexes are developing fast!",
                highlights = listOf("Vocal cords are developing", "Fingers and toes separated", "Kidneys are producing urine")
            )
            in 15..20 -> PregnancyWeekInfo(
                week = week,
                fruitOrVeggie = "Mango",
                emoji = "🥭",
                weight = "300 g",
                length = "25.6 cm",
                trimester = 2,
                daysRemaining = daysRemaining,
                progressPercent = progressPercent,
                description = "Baby can now hear your voice and heartbeat! First tiny flutter kicks appear.",
                highlights = listOf("Vernix caseosa protecting baby's skin", "Baby can swallow and hear sounds", "Sensory cortex developing rapidly")
            )
            in 21..27 -> PregnancyWeekInfo(
                week = week,
                fruitOrVeggie = "Corn",
                emoji = "🌽",
                weight = "600 g",
                length = "30.1 cm",
                trimester = 2,
                daysRemaining = daysRemaining,
                progressPercent = progressPercent,
                description = "Your baby is developing rapidly. Baby can hear your voice and may respond to sounds.",
                highlights = listOf(
                    "Baby's lungs are developing",
                    "Baby can hear sounds",
                    "Baby may startle at loud noises",
                    "Rapid eye movement (REM) sleep begins",
                    "Footprints and palm prints formed"
                )
            )
            in 28..33 -> PregnancyWeekInfo(
                week = week,
                fruitOrVeggie = "Eggplant",
                emoji = "🍆",
                weight = "1.5 kg",
                length = "39.9 cm",
                trimester = 3,
                daysRemaining = daysRemaining,
                progressPercent = progressPercent,
                description = "Baby's brain is creating billions of neurons. Baby can blink and open eyes!",
                highlights = listOf("Eyes can sense light and dark", "Bones hardening while skull stays soft", "Rhythmic breathing practice")
            )
            else -> PregnancyWeekInfo(
                week = week,
                fruitOrVeggie = "Watermelon",
                emoji = "🍉",
                weight = "3.2 kg",
                length = "48.5 cm",
                trimester = 3,
                daysRemaining = daysRemaining,
                progressPercent = progressPercent,
                description = "Baby is full term and ready to meet you! Gaining protective body fat daily.",
                highlights = listOf("Lungs are fully mature", "Head engaged in pelvis", "Immune antibodies received from mother")
            )
        }
    }
}
