package com.example

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.BackHandler
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.ui.components.BottomNavBar
import com.example.ui.components.SideDrawerContent
import com.example.ui.screens.*
import com.example.ui.theme.MomAndBabyTheme
import com.example.ui.viewmodel.BottomTab
import com.example.ui.viewmodel.MomBabyViewModel
import com.example.ui.viewmodel.Screen
import com.example.ui.viewmodel.ThemeMode
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {

    private val viewModel: MomBabyViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            val themeMode by viewModel.themeMode.collectAsStateWithLifecycle()
            val systemInDark = isSystemInDarkTheme()
            val isDark = when (themeMode) {
                ThemeMode.SYSTEM -> systemInDark
                ThemeMode.LIGHT -> false
                ThemeMode.DARK -> true
            }

            MomAndBabyTheme(darkTheme = isDark) {
                MainApp(viewModel = viewModel, themeMode = themeMode)
            }
        }
    }
}

@Composable
fun MainApp(
    viewModel: MomBabyViewModel,
    themeMode: ThemeMode
) {
    val currentScreen by viewModel.currentScreen.collectAsStateWithLifecycle()
    val activeTab by viewModel.activeBottomTab.collectAsStateWithLifecycle()
    val userProfile by viewModel.userProfile.collectAsStateWithLifecycle()
    val currentWeekInfo by viewModel.currentWeekInfo.collectAsStateWithLifecycle()
    val kickCount by viewModel.kickCount.collectAsStateWithLifecycle()
    val waterGlasses by viewModel.waterGlasses.collectAsStateWithLifecycle()
    val appointments by viewModel.appointments.collectAsStateWithLifecycle()
    val vaccines by viewModel.vaccines.collectAsStateWithLifecycle()
    val medications by viewModel.medications.collectAsStateWithLifecycle()
    val growthRecords by viewModel.growthRecords.collectAsStateWithLifecycle()
    val babyMilestones by viewModel.babyMilestones.collectAsStateWithLifecycle()
    val emergencyContacts by viewModel.emergencyContacts.collectAsStateWithLifecycle()
    val articles by viewModel.articles.collectAsStateWithLifecycle()
    val recipes by viewModel.recipes.collectAsStateWithLifecycle()
    val chatMessages by viewModel.chatMessages.collectAsStateWithLifecycle()
    val notifications by viewModel.notifications.collectAsStateWithLifecycle()
    val unreadNotificationsCount by viewModel.unreadNotificationsCount.collectAsStateWithLifecycle()
    val contractions by viewModel.contractions.collectAsStateWithLifecycle()
    val hospitalBagItems by viewModel.hospitalBagItems.collectAsStateWithLifecycle()
    val selectedArticle by viewModel.selectedArticle.collectAsStateWithLifecycle()

    val showAddAppointment by viewModel.showAddAppointmentDialog.collectAsStateWithLifecycle()
    val showAddMedication by viewModel.showAddMedicationDialog.collectAsStateWithLifecycle()
    val showAddContact by viewModel.showAddContactDialog.collectAsStateWithLifecycle()
    val showAddGrowth by viewModel.showAddGrowthDialog.collectAsStateWithLifecycle()
    val showEditProfile by viewModel.showEditProfileDialog.collectAsStateWithLifecycle()

    var showNotificationsSheet by remember { mutableStateOf(false) }

    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val scope = rememberCoroutineScope()

    val isTopLevelScreen = currentScreen in listOf(
        Screen.HOME,
        Screen.CALENDAR,
        Screen.PROGRESS,
        Screen.MESSAGES,
        Screen.PROFILE
    )

    val isAuthOrWelcome = currentScreen in listOf(
        Screen.WELCOME,
        Screen.LOGIN,
        Screen.SIGN_UP,
        Screen.FORGOT_PASSWORD,
        Screen.PREGNANCY_SETUP
    )

    BackHandler(enabled = !isAuthOrWelcome && currentScreen != Screen.HOME) {
        viewModel.navigateTo(Screen.HOME)
    }

    ModalNavigationDrawer(
        drawerState = drawerState,
        gesturesEnabled = isTopLevelScreen,
        drawerContent = {
            SideDrawerContent(
                userProfile = userProfile,
                currentScreen = currentScreen,
                themeMode = themeMode,
                onToggleTheme = { newMode -> viewModel.setThemeMode(newMode) },
                onNavigate = { screen ->
                    viewModel.navigateTo(screen)
                },
                onCloseDrawer = {
                    scope.launch { drawerState.close() }
                }
            )
        }
    ) {
        Scaffold(
            modifier = Modifier.fillMaxSize(),
            bottomBar = {
                if (isTopLevelScreen) {
                    BottomNavBar(
                        selectedTab = activeTab,
                        onTabSelected = { tab ->
                            viewModel.selectBottomTab(tab)
                        }
                    )
                }
            }
        ) { innerPadding ->
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding)
            ) {
                when (currentScreen) {
                    Screen.WELCOME -> WelcomeScreen(
                        onGetStarted = { viewModel.navigateTo(Screen.SIGN_UP) },
                        onLoginClick = { viewModel.navigateTo(Screen.LOGIN) }
                    )

                    Screen.SIGN_UP -> SignUpScreen(
                        onSignUpSuccess = { viewModel.navigateTo(Screen.PREGNANCY_SETUP) },
                        onNavigateToLogin = { viewModel.navigateTo(Screen.LOGIN) },
                        onBack = { viewModel.navigateTo(Screen.WELCOME) }
                    )

                    Screen.LOGIN -> LoginScreen(
                        onLoginSuccess = { viewModel.navigateTo(Screen.HOME) },
                        onNavigateToSignUp = { viewModel.navigateTo(Screen.SIGN_UP) },
                        onForgotPassword = { viewModel.navigateTo(Screen.FORGOT_PASSWORD) },
                        onBack = { viewModel.navigateTo(Screen.WELCOME) }
                    )

                    Screen.FORGOT_PASSWORD -> ForgotPasswordScreen(
                        onBack = { viewModel.navigateTo(Screen.LOGIN) },
                        onResetSent = { viewModel.navigateTo(Screen.LOGIN) }
                    )

                    Screen.PREGNANCY_SETUP -> PregnancySetupScreen(
                        onSetupComplete = { dueDate, nickname, doctor, hospital ->
                            viewModel.updateUserProfile(
                                userProfile.name,
                                userProfile.email,
                                dueDate,
                                nickname,
                                doctor,
                                hospital,
                                userProfile.partnerName,
                                userProfile.partnerPhone
                            )
                            viewModel.navigateTo(Screen.HOME)
                        },
                        onBack = { viewModel.navigateTo(Screen.WELCOME) }
                    )

                    Screen.HOME -> HomeScreen(
                        userProfile = userProfile,
                        weekInfo = currentWeekInfo,
                        unreadNotificationsCount = unreadNotificationsCount,
                        onOpenDrawer = { scope.launch { drawerState.open() } },
                        onOpenNotifications = { showNotificationsSheet = true },
                        onNavigate = { screen -> viewModel.navigateTo(screen) }
                    )

                    Screen.PREGNANCY_TRACKER -> PregnancyTrackerScreen(
                        weekInfo = currentWeekInfo,
                        kickCount = kickCount,
                        onIncrementKick = { viewModel.incrementKick() },
                        onResetKicks = { viewModel.resetKicks() },
                        onSelectWeek = { week -> viewModel.setWeek(week) },
                        onBack = { viewModel.navigateTo(Screen.HOME) }
                    )

                    Screen.APPOINTMENTS -> AppointmentsScreen(
                        appointments = appointments,
                        onAddAppointmentClick = { viewModel.setShowAddAppointment(true) },
                        onDeleteAppointment = { id -> viewModel.deleteAppointment(id) },
                        onBack = { viewModel.navigateTo(Screen.HOME) }
                    )

                    Screen.VACCINES -> VaccinationScreen(
                        vaccines = vaccines,
                        onToggleVaccine = { id -> viewModel.toggleVaccine(id) },
                        onBack = { viewModel.navigateTo(Screen.HOME) }
                    )

                    Screen.BABY_GROWTH -> BabyGrowthScreen(
                        growthRecords = growthRecords,
                        milestones = babyMilestones,
                        onAddGrowthRecord = { viewModel.setShowAddGrowth(true) },
                        onToggleMilestone = { id -> viewModel.toggleMilestone(id) },
                        onBack = { viewModel.navigateTo(Screen.HOME) }
                    )

                    Screen.NUTRITION -> NutritionScreen(
                        waterGlasses = waterGlasses,
                        recipes = recipes,
                        onIncrementWater = { viewModel.incrementWater() },
                        onDecrementWater = { viewModel.decrementWater() },
                        onBack = { viewModel.navigateTo(Screen.HOME) }
                    )

                    Screen.HEALTH_EDUCATION -> HealthEducationScreen(
                        articles = articles,
                        onSelectArticle = { article -> viewModel.openArticle(article) },
                        onToggleBookmark = { id -> viewModel.toggleBookmarkArticle(id) },
                        onBack = { viewModel.navigateTo(Screen.HOME) }
                    )

                    Screen.ARTICLE_DETAIL -> ArticleDetailScreen(
                        article = selectedArticle,
                        onToggleBookmark = { id -> viewModel.toggleBookmarkArticle(id) },
                        onBack = { viewModel.navigateTo(Screen.HEALTH_EDUCATION) }
                    )

                    Screen.MEDICATIONS -> MedicationScreen(
                        medications = medications,
                        onToggleMedication = { id -> viewModel.toggleMedicationTaken(id) },
                        onAddMedicationClick = { viewModel.setShowAddMedication(true) },
                        onDeleteMedication = { id -> viewModel.deleteMedication(id) },
                        onBack = { viewModel.navigateTo(Screen.HOME) }
                    )

                    Screen.EMERGENCY_CONTACTS -> EmergencyContactsScreen(
                        contacts = emergencyContacts,
                        onAddContact = { viewModel.setShowAddContact(true) },
                        onDeleteContact = { id -> viewModel.deleteEmergencyContact(id) },
                        onBack = { viewModel.navigateTo(Screen.HOME) }
                    )

                    Screen.HOSPITAL_BAG -> HospitalBagScreen(
                        items = hospitalBagItems,
                        onToggleItem = { id -> viewModel.toggleHospitalBagItem(id) },
                        onAddItem = { name, cat, qty -> viewModel.addHospitalBagItem(name, cat, qty) },
                        onBack = { viewModel.navigateTo(Screen.HOME) }
                    )

                    Screen.CONTRACTION_TIMER -> ContractionTimerScreen(
                        contractions = contractions,
                        onAddContraction = { dur, intv, inten -> viewModel.addContraction(dur, intv, inten) },
                        onClearContractions = { viewModel.clearContractions() },
                        onBack = { viewModel.navigateTo(Screen.HOME) }
                    )

                    Screen.CALENDAR -> CalendarScreen(
                        appointments = appointments,
                        vaccines = vaccines,
                        onAddAppointmentClick = { viewModel.setShowAddAppointment(true) }
                    )

                    Screen.PROGRESS -> ProgressScreen(
                        weekInfo = currentWeekInfo,
                        kickCount = kickCount,
                        waterGlasses = waterGlasses
                    )

                    Screen.MESSAGES -> MessagesScreen(
                        messages = chatMessages,
                        onSendMessage = { text -> viewModel.sendChatMessage(text) }
                    )

                    Screen.PROFILE -> ProfileScreen(
                        userProfile = userProfile,
                        themeMode = themeMode,
                        onSelectThemeMode = { newMode -> viewModel.setThemeMode(newMode) },
                        onEditProfileClick = { viewModel.setShowEditProfile(true) },
                        onLogout = { viewModel.navigateTo(Screen.WELCOME) }
                    )

                    Screen.SETTINGS, Screen.HELP_SUPPORT -> ProfileScreen(
                        userProfile = userProfile,
                        themeMode = themeMode,
                        onSelectThemeMode = { newMode -> viewModel.setThemeMode(newMode) },
                        onEditProfileClick = { viewModel.setShowEditProfile(true) },
                        onLogout = { viewModel.navigateTo(Screen.WELCOME) }
                    )
                }
            }
        }
    }

    if (showNotificationsSheet) {
        NotificationBottomSheet(
            notifications = notifications,
            onDismiss = { showNotificationsSheet = false },
            onMarkAllRead = { viewModel.markAllNotificationsRead() }
        )
    }

    if (showAddAppointment) {
        AddAppointmentDialog(
            onDismiss = { viewModel.setShowAddAppointment(false) },
            onConfirm = { t, h, d, m, dy, y, tm, n ->
                viewModel.addAppointment(t, h, d, m, dy, y, tm, n)
            }
        )
    }

    if (showAddMedication) {
        AddMedicationDialog(
            onDismiss = { viewModel.setShowAddMedication(false) },
            onConfirm = { n, d, t, f ->
                viewModel.addMedication(n, d, t, f)
            }
        )
    }

    if (showAddContact) {
        AddEmergencyContactDialog(
            onDismiss = { viewModel.setShowAddContact(false) },
            onConfirm = { n, r, p ->
                viewModel.addEmergencyContact(n, r, p)
            }
        )
    }

    if (showAddGrowth) {
        AddGrowthDialog(
            onDismiss = { viewModel.setShowAddGrowth(false) },
            onConfirm = { p, w, h, hd, bmi, dt ->
                viewModel.addGrowthRecord(p, w, h, hd, bmi, dt)
            }
        )
    }

    if (showEditProfile) {
        EditProfileDialog(
            profile = userProfile,
            onDismiss = { viewModel.setShowEditProfile(false) },
            onConfirm = { n, e, dd, nick, doc, hosp, part, partPh ->
                viewModel.updateProfile(n, e, dd, nick, doc, hosp, part, partPh)
            }
        )
    }
}
