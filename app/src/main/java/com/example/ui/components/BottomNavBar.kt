package com.example.ui.components

import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material.icons.outlined.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ui.theme.*
import com.example.ui.viewmodel.BottomTab

@Composable
fun BottomNavBar(
    selectedTab: BottomTab,
    onTabSelected: (BottomTab) -> Unit,
    modifier: Modifier = Modifier
) {
    val colors = AppTheme.colors
    NavigationBar(
        containerColor = colors.surface,
        tonalElevation = 8.dp,
        modifier = modifier
    ) {
        val items = listOf(
            Triple(BottomTab.HOME, "Home", Pair(Icons.Filled.Home, Icons.Outlined.Home)),
            Triple(BottomTab.CALENDAR, "Calendar", Pair(Icons.Filled.CalendarMonth, Icons.Outlined.CalendarMonth)),
            Triple(BottomTab.PROGRESS, "Progress", Pair(Icons.Filled.TrendingUp, Icons.Outlined.TrendingUp)),
            Triple(BottomTab.MESSAGES, "Messages", Pair(Icons.Filled.ChatBubble, Icons.Outlined.ChatBubbleOutline)),
            Triple(BottomTab.PROFILE, "Profile", Pair(Icons.Filled.Person, Icons.Outlined.PersonOutline))
        )

        items.forEach { (tab, label, icons) ->
            val isSelected = selectedTab == tab
            NavigationBarItem(
                selected = isSelected,
                onClick = { onTabSelected(tab) },
                icon = {
                    Icon(
                        imageVector = if (isSelected) icons.first else icons.second,
                        contentDescription = label,
                        modifier = Modifier.size(22.dp)
                    )
                },
                label = {
                    Text(
                        text = label,
                        style = MaterialTheme.typography.labelSmall.copy(
                            fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal,
                            fontSize = 11.sp
                        )
                    )
                },
                colors = NavigationBarItemDefaults.colors(
                    selectedIconColor = colors.primary,
                    selectedTextColor = colors.primary,
                    unselectedIconColor = colors.textSecondary,
                    unselectedTextColor = colors.textSecondary,
                    indicatorColor = colors.softPink
                ),
                modifier = Modifier.testTag("bottom_tab_${label.lowercase()}")
            )
        }
    }
}
