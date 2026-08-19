package com.example.data.repository

import com.example.data.model.*
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class MomBabyRepository {

    private val _userProfile = MutableStateFlow(UserProfile())
    val userProfile: StateFlow<UserProfile> = _userProfile.asStateFlow()

    private val _currentWeek = MutableStateFlow(24)
    val currentWeek: StateFlow<Int> = _currentWeek.asStateFlow()

    private val _kickCount = MutableStateFlow(4)
    val kickCount: StateFlow<Int> = _kickCount.asStateFlow()

    private val _waterGlasses = MutableStateFlow(6)
    val waterGlasses: StateFlow<Int> = _waterGlasses.asStateFlow()

    private val _appointments = MutableStateFlow(
        listOf(
            Appointment(
                id = "apt-1",
                title = "24-Week Anomaly & Growth Ultrasound",
                hospital = "General Hospital Maternity Clinic",
                doctor = "Dr. Adaeze Johnson",
                month = "OCT",
                day = "24",
                year = "2024",
                time = "10:30 AM",
                isPast = false,
                notes = "Check fetal cardiac chambers, kidney formation, and amniotic fluid index."
            ),
            Appointment(
                id = "apt-2",
                title = "Glucose Tolerance Screening Test",
                hospital = "LifeCare Diagnostic Center",
                doctor = "Dr. Adaeze Johnson",
                month = "NOV",
                day = "08",
                year = "2024",
                time = "08:00 AM",
                isPast = false,
                notes = "Fasting for 8 hours required prior to arrival."
            ),
            Appointment(
                id = "apt-3",
                title = "Initial First Trimester Registration",
                hospital = "General Hospital Maternity Clinic",
                doctor = "Dr. Adaeze Johnson",
                month = "AUG",
                day = "12",
                year = "2024",
                time = "09:00 AM",
                isPast = true,
                notes = "Blood routine, baseline BP 115/75, ultrasound confirmed single intrauterine pregnancy."
            )
        )
    )
    val appointments: StateFlow<List<Appointment>> = _appointments.asStateFlow()

    private val _vaccines = MutableStateFlow(
        listOf(
            VaccineItem(
                id = "vac-1",
                title = "Tetanus Toxoid 1 (TT1)",
                recommendedTime = "First trimester / Early registration",
                trimester = 1,
                doses = "Dose 1 of 5",
                description = "Protects both mother and neonate from tetanus infection during labor.",
                isCompleted = true
            ),
            VaccineItem(
                id = "vac-2",
                title = "Tetanus Toxoid 2 (TT2)",
                recommendedTime = "4 weeks after TT1 (Around Week 16-20)",
                trimester = 2,
                doses = "Dose 2 of 5",
                description = "Boosts protective immunity against maternal and neonatal tetanus.",
                isCompleted = true
            ),
            VaccineItem(
                id = "vac-3",
                title = "Tdap (Tetanus, Diphtheria, Pertussis)",
                recommendedTime = "Between Week 27 and Week 36",
                trimester = 3,
                doses = "Single booster each pregnancy",
                description = "Transfers high levels of pertussis (whooping cough) antibodies to newborn before birth.",
                isCompleted = false
            ),
            VaccineItem(
                id = "vac-4",
                title = "Influenza (Flu Shot)",
                recommendedTime = "Any trimester during flu season",
                trimester = 2,
                doses = "Single seasonal dose",
                description = "Protects expecting mothers against severe viral respiratory illnesses.",
                isCompleted = true
            )
        )
    )
    val vaccines: StateFlow<List<VaccineItem>> = _vaccines.asStateFlow()

    private val _medications = MutableStateFlow(
        listOf(
            MedicationItem("med-1", "Prenatal Multivitamin with Folic Acid", "1 tablet (800 mcg Folate)", "08:00 AM", "Once daily", true),
            MedicationItem("med-2", "Ferrous Sulfate (Iron supplement)", "200 mg tablet", "01:00 PM", "Daily after lunch", false),
            MedicationItem("med-3", "Calcium Carbonate + Vitamin D3", "500 mg / 400 IU", "08:00 PM", "Daily with dinner", false)
        )
    )
    val medications: StateFlow<List<MedicationItem>> = _medications.asStateFlow()

    private val _growthRecords = MutableStateFlow(
        listOf(
            BabyGrowthRecord("gr-0", "Birth", 3.2f, 49.5f, 34.0f, 13.1f, "Day 0"),
            BabyGrowthRecord("gr-1", "1 Month", 4.2f, 54.0f, 37.0f, 14.4f, "1M"),
            BabyGrowthRecord("gr-2", "2 Months", 5.4f, 57.8f, 39.2f, 16.1f, "2M"),
            BabyGrowthRecord("gr-3", "3 Months", 6.2f, 61.0f, 40.8f, 16.6f, "3M"),
            BabyGrowthRecord("gr-4", "4 Months", 7.0f, 63.5f, 42.0f, 17.3f, "4M"),
            BabyGrowthRecord("gr-5", "6 Months", 7.9f, 67.2f, 43.5f, 17.5f, "6M")
        )
    )
    val growthRecords: StateFlow<List<BabyGrowthRecord>> = _growthRecords.asStateFlow()

    private val _babyMilestones = MutableStateFlow(
        listOf(
            BabyMilestone("ms-1", "Smiles responsively at mother's voice", "1-2 Months", "Social", true, "Baby connects visually and responds to parent speaking."),
            BabyMilestone("ms-2", "Holds head steady during tummy time", "2-3 Months", "Motor", true, "Lifts head 45 to 90 degrees while lying on stomach."),
            BabyMilestone("ms-3", "Reaches and grasps favorite rattle", "3-4 Months", "Motor", false, "Coordinates hand-eye movement to grab objects."),
            BabyMilestone("ms-4", "Rolls from tummy to back", "4-6 Months", "Motor", false, "Uses abdominal strength to flip positions independently.")
        )
    )
    val babyMilestones: StateFlow<List<BabyMilestone>> = _babyMilestones.asStateFlow()

    private val _emergencyContacts = MutableStateFlow(
        listOf(
            EmergencyContact("ec-1", "Dr. Adaeze Johnson (Obstetrician)", "Primary Obstetrician", "+234 802 345 6789", true),
            EmergencyContact("ec-2", "Emeka Okon (Partner / Husband)", "Partner & Primary Support", "+234 801 234 5678", true),
            EmergencyContact("ec-3", "General Hospital Maternity Triage", "Hospital Emergency Line", "112 / +234 800 555 4321", true),
            EmergencyContact("ec-4", "Grace Okon (Mother-in-Law)", "Family Support", "+234 803 987 6543", false)
        )
    )
    val emergencyContacts: StateFlow<List<EmergencyContact>> = _emergencyContacts.asStateFlow()

    private val _articles = MutableStateFlow(
        listOf(
            Article(
                id = "art-1",
                title = "Signs of Labor: When to Head to the Hospital",
                readTime = "5 min read",
                category = "Labor & Delivery",
                summary = "Recognizing real contractions versus Braxton Hicks, water breaking signs, and the 5-1-1 labor rule.",
                content = """
                    Understanding the signs of true labor helps you stay calm and confident as your delivery date approaches.
                    
                    1. True vs False Contractions:
                    - Braxton Hicks contractions are irregular, painless or mild, and subside when you change position or drink water.
                    - True labor contractions come at regular intervals, progressively become closer together, last 45 to 60 seconds, and grow in intensity regardless of resting.
                    
                    2. The 5-1-1 Rule:
                    When contractions are 5 minutes apart, lasting 1 full minute each, for at least 1 consecutive hour, contact your doctor or proceed to the maternity ward.
                    
                    3. Membrane Rupture (Water Breaking):
                    Note the time, color, and amount of amniotic fluid. Clear or pale straw fluid is normal; if green or brown, notify your obstetrician immediately.
                """.trimIndent(),
                isBookmarked = true
            ),
            Article(
                id = "art-2",
                title = "Essential Newborn Care: First 30 Days",
                readTime = "6 min read",
                category = "Baby Care",
                summary = "A comprehensive guide on safe sleep guidelines, umbilical cord care, sponge baths, and hunger cues.",
                content = """
                    Caring for your newborn in the initial weeks is all about bonding, establishing feeding, and recognizing early cues.
                    
                    - Safe Sleep: Always place baby on their back on a firm, flat mattress with no loose blankets or pillows.
                    - Umbilical Cord: Keep the stump clean and dry until it naturally falls off around 7-14 days.
                    - Sponge Baths: Gentle lukewarm sponge baths are best until the cord site completely heals.
                """.trimIndent(),
                isBookmarked = false
            ),
            Article(
                id = "art-3",
                title = "Optimal Nutrition & Superfoods in Pregnancy",
                readTime = "4 min read",
                category = "Nutrition",
                summary = "Top foods rich in DHA, folate, iron, and choline to maximize fetal brain and bone growth.",
                content = """
                    A colorful plate guarantees a wide spectrum of micronutrients.
                    
                    - Leafy Greens: High in natural folates.
                    - Eggs: Loaded with choline for fetal brain development.
                    - Salmon & Chia Seeds: High in Omega-3 DHA for retinal and cognitive health.
                    - Citrus Fruits: Boost iron absorption when paired with whole grains.
                """.trimIndent(),
                isBookmarked = true
            )
        )
    )
    val articles: StateFlow<List<Article>> = _articles.asStateFlow()

    private val _recipes = MutableStateFlow(
        listOf(
            Recipe(
                id = "rec-1",
                title = "Avocado & Berry Quinoa Super Bowl",
                prepTime = "15 mins",
                calories = "380 kcal",
                category = "Breakfast / Lunch",
                benefits = "Rich in healthy monounsaturated fats, folate, antioxidants, and complete protein.",
                ingredients = listOf("1/2 ripe avocado sliced", "1 cup cooked quinoa", "1/2 cup fresh blueberries", "1 tbsp chia seeds", "1 cup baby spinach", "1 tbsp olive oil & lemon dressing"),
                instructions = listOf("Place quinoa and fresh spinach in a bowl.", "Top with sliced avocado, blueberries, and sprinkle chia seeds.", "Drizzle olive oil lemon dressing and enjoy!")
            ),
            Recipe(
                id = "rec-2",
                title = "Iron-Rich Spinach & Lentil Soup",
                prepTime = "25 mins",
                calories = "320 kcal",
                category = "Dinner",
                benefits = "Boosts maternal hemoglobin and provides plant-based protein with gentle fiber.",
                ingredients = listOf("1 cup brown lentils rinsed", "2 cups fresh chopped spinach", "1 diced carrot", "2 cloves minced garlic", "4 cups vegetable broth", "1 lemon squeezed"),
                instructions = listOf("Sauté garlic and carrots in a pot for 3 minutes.", "Add rinsed lentils and vegetable broth; bring to a boil.", "Simmer for 20 minutes until lentils are tender.", "Stir in spinach and fresh lemon juice before serving warm.")
            )
        )
    )
    val recipes: StateFlow<List<Recipe>> = _recipes.asStateFlow()

    private val _chatMessages = MutableStateFlow(
        listOf(
            ChatMessage("msg-1", "Dr. Adaeze Johnson", "Hello Blessing! How are you and baby feeling today at Week 24?", "09:15 AM", false),
            ChatMessage("msg-2", "Blessing", "Good morning Doctor! Baby has been very active this morning with lots of kicks.", "09:18 AM", true),
            ChatMessage("msg-3", "Dr. Adaeze Johnson", "That is wonderful to hear! 10 kicks within 2 hours is our reassurance benchmark. Don't forget your anomaly ultrasound this Thursday.", "09:20 AM", false)
        )
    )
    val chatMessages: StateFlow<List<ChatMessage>> = _chatMessages.asStateFlow()

    private val _notifications = MutableStateFlow(
        listOf(
            NotificationItem("notif-1", "Upcoming Appointment", "24-Week Ultrasound scheduled for Thursday at 10:30 AM", "2 hours ago", false),
            NotificationItem("notif-2", "Hydration Reminder", "You've had 6 of 10 glasses today. Drink a glass of water now!", "4 hours ago", false),
            NotificationItem("notif-3", "Weekly Milestone", "Week 24: Baby is the size of an ear of corn (600g)!", "1 day ago", true)
        )
    )
    val notifications: StateFlow<List<NotificationItem>> = _notifications.asStateFlow()

    private val _calendarEvents = MutableStateFlow(
        listOf(
            CalendarDayEvent("ev-1", 24, 10, 2024, "24-Week Ultrasound", "10:30 AM", "Appointment", 0xFFE83E8C),
            CalendarDayEvent("ev-2", 8, 11, 2024, "Glucose Screening", "08:00 AM", "Lab Test", 0xFF8E24AA)
        )
    )
    val calendarEvents: StateFlow<List<CalendarDayEvent>> = _calendarEvents.asStateFlow()

    private val _contractions = MutableStateFlow(
        listOf(
            ContractionRecord("ct-1", "08:45 AM", 55, 6, "Moderate"),
            ContractionRecord("ct-2", "08:51 AM", 60, 5, "Strong"),
            ContractionRecord("ct-3", "08:56 AM", 62, 5, "Strong")
        )
    )
    val contractions: StateFlow<List<ContractionRecord>> = _contractions.asStateFlow()

    private val _hospitalBagItems = MutableStateFlow(
        listOf(
            HospitalBagItem("hb-1", "Important Documents", "ID Card & Hospital Registration File", true, "1x"),
            HospitalBagItem("hb-2", "Important Documents", "Insurance Card & Antenatal Health Records", true, "1x"),
            HospitalBagItem("hb-3", "Important Documents", "Birth Plan Copies", false, "2x"),
            HospitalBagItem("hb-4", "Mom", "Comfortable Nursing Gowns & Robe", true, "2x"),
            HospitalBagItem("hb-5", "Mom", "Maternity Pads & Disposable Briefs", true, "1 pack"),
            HospitalBagItem("hb-6", "Mom", "Supportive Nursing Bras & Breast Pads", false, "2x"),
            HospitalBagItem("hb-7", "Mom", "Lip Balm, Warm Socks & Slippers", true, "1 set"),
            HospitalBagItem("hb-8", "Baby", "Going-Home Onesie / Sleepsuits", true, "3x"),
            HospitalBagItem("hb-9", "Baby", "Soft Baby Blanket / Swaddle", true, "2x"),
            HospitalBagItem("hb-10", "Baby", "Newborn Diapers & Gentle Wet Wipes", true, "1 pack"),
            HospitalBagItem("hb-11", "Partner", "Phone Chargers & Long Cables", true, "2x"),
            HospitalBagItem("hb-12", "Partner", "Change of Clothes & Energy Snacks", false, "1 bag")
        )
    )
    val hospitalBagItems: StateFlow<List<HospitalBagItem>> = _hospitalBagItems.asStateFlow()

    fun setPregnancyWeek(week: Int) {
        _currentWeek.value = week.coerceIn(4, 40)
        _userProfile.update { it.copy(currentWeek = week) }
    }

    fun incrementKicks() { _kickCount.update { it + 1 } }
    fun resetKicks() { _kickCount.value = 0 }
    fun incrementWater() { _waterGlasses.update { (it + 1).coerceAtMost(16) } }
    fun decrementWater() { _waterGlasses.update { (it - 1).coerceAtLeast(0) } }

    fun toggleMedicationTaken(id: String) {
        _medications.update { list ->
            list.map { if (it.id == id) it.copy(isTaken = !it.isTaken) else it }
        }
    }

    fun addMedication(name: String, dosage: String, time: String, frequency: String) {
        val newMed = MedicationItem(
            id = "med-${System.currentTimeMillis()}",
            name = name,
            dosage = dosage,
            time = time,
            frequency = frequency,
            isTaken = false
        )
        _medications.update { it + newMed }
    }

    fun deleteMedication(id: String) {
        _medications.update { list -> list.filterNot { it.id == id } }
    }

    fun toggleVaccineCompleted(id: String) {
        _vaccines.update { list ->
            list.map { if (it.id == id) it.copy(isCompleted = !it.isCompleted) else it }
        }
    }

    fun addAppointment(title: String, hospital: String, doctor: String, month: String, day: String, year: String, time: String, notes: String) {
        val newApt = Appointment(
            id = "apt-${System.currentTimeMillis()}",
            title = title,
            hospital = hospital,
            doctor = doctor,
            month = month,
            day = day,
            year = year,
            time = time,
            isPast = false,
            notes = notes
        )
        _appointments.update { listOf(newApt) + it }
    }

    fun deleteAppointment(id: String) {
        _appointments.update { list -> list.filterNot { it.id == id } }
    }

    fun addEmergencyContact(name: String, relation: String, phone: String) {
        val newContact = EmergencyContact(
            id = "ec-${System.currentTimeMillis()}",
            name = name,
            relationship = relation,
            phoneNumber = phone,
            isQuickCall = false
        )
        _emergencyContacts.update { it + newContact }
    }

    fun deleteEmergencyContact(id: String) {
        _emergencyContacts.update { list -> list.filterNot { it.id == id } }
    }

    fun addGrowthRecord(period: String, weight: Float, height: Float, head: Float, bmi: Float, date: String) {
        val newRecord = BabyGrowthRecord(
            id = "gr-${System.currentTimeMillis()}",
            period = period,
            weightKg = weight,
            heightCm = height,
            headCircumferenceCm = head,
            bmi = bmi,
            recordedDate = date
        )
        _growthRecords.update { it + newRecord }
    }

    fun toggleMilestone(id: String) {
        _babyMilestones.update { list ->
            list.map { if (it.id == id) it.copy(isAchieved = !it.isAchieved) else it }
        }
    }

    fun toggleBookmarkArticle(id: String) {
        _articles.update { list ->
            list.map { if (it.id == id) it.copy(isBookmarked = !it.isBookmarked) else it }
        }
    }

    fun addContraction(durationSeconds: Int, intervalMinutes: Int, intensity: String = "Moderate") {
        val newRecord = ContractionRecord(
            id = "ct-${System.currentTimeMillis()}",
            startTime = "Just now",
            durationSeconds = durationSeconds,
            intervalMinutes = intervalMinutes,
            intensity = intensity
        )
        _contractions.update { listOf(newRecord) + it }
    }

    fun clearContractions() { _contractions.value = emptyList() }

    fun toggleHospitalBagItem(id: String) {
        _hospitalBagItems.update { list ->
            list.map { if (it.id == id) it.copy(isPacked = !it.isPacked) else it }
        }
    }

    fun addHospitalBagItem(name: String, category: String, quantity: String) {
        val newItem = HospitalBagItem(
            id = "hb-${System.currentTimeMillis()}",
            name = name,
            category = category,
            isPacked = false,
            quantityNote = quantity
        )
        _hospitalBagItems.update { it + newItem }
    }

    fun sendChatMessage(text: String) {
        val userMsg = ChatMessage(
            id = "msg-${System.currentTimeMillis()}",
            senderName = "Blessing",
            text = text,
            timestamp = "Just now",
            isFromUser = true
        )
        _chatMessages.update { it + userMsg }

        val replyText = when {
            text.contains("kick", ignoreCase = true) || text.contains("move", ignoreCase = true) ->
                "Regular fetal movements are a reassuring sign of healthy neurological development! Keep tracking 10 kicks within 2 hours."
            text.contains("pain", ignoreCase = true) || text.contains("cramp", ignoreCase = true) ->
                "Mild stretching sensations are normal as your round ligaments expand. However, if pain is rhythmic or sharp, please contact our maternity triage."
            else ->
                "Thank you for your update, Blessing! Dr. Adaeze and the nursing team have noted this."
        }
        val doctorMsg = ChatMessage(
            id = "msg-${System.currentTimeMillis() + 1}",
            senderName = "Dr. Adaeze Johnson",
            text = replyText,
            timestamp = "Just now",
            isFromUser = false
        )
        _chatMessages.update { it + doctorMsg }
    }

    fun markAllNotificationsRead() {
        _notifications.update { list -> list.map { it.copy(isRead = true) } }
    }

    fun updateProfile(name: String, email: String, dueDate: String, nickname: String, doctor: String, hospital: String, partner: String, partnerPhone: String) {
        _userProfile.update {
            it.copy(
                name = name,
                email = email,
                dueDate = dueDate,
                babyNickname = nickname,
                doctorName = doctor,
                hospitalName = hospital,
                partnerName = partner,
                partnerPhone = partnerPhone
            )
        }
    }
}
