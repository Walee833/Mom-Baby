package com.example.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Luggage
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
import com.example.data.model.HospitalBagItem
import com.example.ui.components.AppHeader
import com.example.ui.components.SegmentedTabBar
import com.example.ui.theme.*

@Composable
fun HospitalBagScreen(
    items: List<HospitalBagItem>,
    onToggleItem: (String) -> Unit,
    onAddItem: (name: String, category: String, quantity: String) -> Unit,
    onBack: () -> Unit,
    modifier: Modifier = Modifier
) {
    var selectedCategoryIndex by remember { mutableIntStateOf(0) }
    val categories = listOf("All", "Documents", "Mom", "Baby", "Partner")
    var showAddDialog by remember { mutableStateOf(false) }

    val filteredItems = when (selectedCategoryIndex) {
        1 -> items.filter { it.category.contains("Documents", ignoreCase = true) }
        2 -> items.filter { it.category.equals("Mom", ignoreCase = true) }
        3 -> items.filter { it.category.equals("Baby", ignoreCase = true) }
        4 -> items.filter { it.category.equals("Partner", ignoreCase = true) }
        else -> items
    }

    val packedCount = items.count { it.isPacked }
    val totalCount = items.size

    Surface(
        color = BackgroundLight,
        modifier = modifier.fillMaxSize()
    ) {
        Column(modifier = Modifier.fillMaxSize()) {
            AppHeader(
                title = "Hospital Bag Checklist",
                onBackClick = onBack,
                rightIcon = Icons.Default.Add,
                onRightIconClick = { showAddDialog = true }
            )

            // Packing Progress Banner
            Surface(
                color = SurfaceWhite,
                shape = RoundedCornerShape(20.dp),
                border = androidx.compose.foundation.BorderStroke(1.dp, SoftPink),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp, vertical = 6.dp)
            ) {
                Row(
                    modifier = Modifier.padding(16.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Box(
                        modifier = Modifier
                            .size(46.dp)
                            .clip(CircleShape)
                            .background(LightOrange),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            imageVector = Icons.Default.Luggage,
                            contentDescription = null,
                            tint = WarningOrange,
                            modifier = Modifier.size(24.dp)
                        )
                    }

                    Spacer(modifier = Modifier.width(14.dp))

                    Column(modifier = Modifier.weight(1f)) {
                        Text(
                            text = "Packed: $packedCount of $totalCount items (${if (totalCount > 0) (packedCount * 100 / totalCount) else 0}%)",
                            style = MaterialTheme.typography.titleMedium.copy(
                                fontWeight = FontWeight.Bold,
                                color = TextPrimary,
                                fontSize = 14.sp
                            )
                        )
                        Spacer(modifier = Modifier.height(4.dp))
                        LinearProgressIndicator(
                            progress = { if (totalCount > 0) packedCount.toFloat() / totalCount.toFloat() else 0f },
                            color = WarningOrange,
                            trackColor = SoftPink,
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(6.dp)
                                .clip(RoundedCornerShape(3.dp))
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(8.dp))

            SegmentedTabBar(
                tabs = categories,
                selectedIndex = selectedCategoryIndex,
                onTabSelected = { selectedCategoryIndex = it },
                modifier = Modifier.testTag("bag_tabs")
            )

            Spacer(modifier = Modifier.height(14.dp))

            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 20.dp),
                verticalArrangement = Arrangement.spacedBy(10.dp),
                contentPadding = PaddingValues(bottom = 32.dp)
            ) {
                items(filteredItems, key = { it.id }) { item ->
                    HospitalBagItemCard(
                        item = item,
                        onToggle = { onToggleItem(item.id) }
                    )
                }
            }
        }
    }

    if (showAddDialog) {
        var name by remember { mutableStateOf("") }
        var category by remember { mutableStateOf("Mom") }
        var quantity by remember { mutableStateOf("1x") }

        AlertDialog(
            onDismissRequest = { showAddDialog = false },
            title = { Text("Add Packing Item", fontWeight = FontWeight.Bold) },
            text = {
                Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
                    OutlinedTextField(
                        value = name,
                        onValueChange = { name = it },
                        label = { Text("Item Name") },
                        placeholder = { Text("e.g. Lip balm, Slippers") },
                        modifier = Modifier.fillMaxWidth()
                    )
                    OutlinedTextField(
                        value = quantity,
                        onValueChange = { quantity = it },
                        label = { Text("Quantity") },
                        modifier = Modifier.fillMaxWidth()
                    )
                }
            },
            confirmButton = {
                Button(
                    onClick = {
                        if (name.isNotBlank()) {
                            onAddItem(name, category, quantity)
                            showAddDialog = false
                        }
                    },
                    colors = ButtonDefaults.buttonColors(containerColor = PrimaryPink)
                ) {
                    Text("Add Item")
                }
            },
            dismissButton = {
                TextButton(onClick = { showAddDialog = false }) { Text("Cancel") }
            }
        )
    }
}

@Composable
private fun HospitalBagItemCard(
    item: HospitalBagItem,
    onToggle: () -> Unit
) {
    Surface(
        color = SurfaceWhite,
        shape = RoundedCornerShape(18.dp),
        border = androidx.compose.foundation.BorderStroke(1.dp, if (item.isPacked) LightGreen else SoftPink),
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onToggle)
            .testTag("bag_item_${item.id}")
    ) {
        Row(
            modifier = Modifier.padding(14.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .size(26.dp)
                    .clip(CircleShape)
                    .background(if (item.isPacked) SuccessGreen else SoftPink),
                contentAlignment = Alignment.Center
            ) {
                if (item.isPacked) {
                    Icon(Icons.Default.Check, contentDescription = null, tint = Color.White, modifier = Modifier.size(16.dp))
                }
            }

            Spacer(modifier = Modifier.width(12.dp))

            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = item.name,
                    style = MaterialTheme.typography.titleMedium.copy(
                        fontWeight = FontWeight.Bold,
                        color = if (item.isPacked) TextSecondary else TextPrimary,
                        fontSize = 13.sp
                    )
                )
                Text(
                    text = "${item.category} · ${item.quantityNote}",
                    style = MaterialTheme.typography.bodySmall.copy(color = PrimaryPinkDark, fontSize = 11.sp)
                )
            }
        }
    }
}
