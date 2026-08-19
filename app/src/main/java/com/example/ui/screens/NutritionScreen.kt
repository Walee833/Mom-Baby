package com.example.ui.screens

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ExpandLess
import androidx.compose.material.icons.filled.ExpandMore
import androidx.compose.material.icons.filled.Remove
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
import com.example.data.model.Recipe
import com.example.ui.components.AppHeader
import com.example.ui.components.PrimaryPinkButton
import com.example.ui.theme.*

@Composable
fun NutritionScreen(
    waterGlasses: Int,
    recipes: List<Recipe>,
    onIncrementWater: () -> Unit,
    onDecrementWater: () -> Unit,
    onBack: () -> Unit,
    modifier: Modifier = Modifier
) {
    Surface(
        color = BackgroundLight,
        modifier = modifier.fillMaxSize()
    ) {
        Column(modifier = Modifier.fillMaxSize()) {
            AppHeader(
                title = "Nutrition & Meals",
                onBackClick = onBack
            )

            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 20.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp),
                contentPadding = PaddingValues(bottom = 32.dp)
            ) {
                // Hydration Tracker
                item {
                    Surface(
                        color = LightBlue,
                        shape = RoundedCornerShape(24.dp),
                        border = androidx.compose.foundation.BorderStroke(1.dp, SoftPink),
                        modifier = Modifier.fillMaxWidth().testTag("hydration_card")
                    ) {
                        Column(
                            modifier = Modifier.padding(18.dp),
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Text(
                                text = "Daily Hydration Tracker 💧",
                                style = MaterialTheme.typography.titleMedium.copy(
                                    fontWeight = FontWeight.Bold,
                                    color = InfoBlue
                                )
                            )
                            Text(
                                text = "Target: 10 glasses (2.5 Liters) for optimal amniotic fluid",
                                style = MaterialTheme.typography.bodySmall.copy(color = TextSecondary)
                            )

                            Spacer(modifier = Modifier.height(14.dp))

                            Text(
                                text = "$waterGlasses / 10",
                                style = MaterialTheme.typography.headlineLarge.copy(
                                    fontWeight = FontWeight.Bold,
                                    color = InfoBlue,
                                    fontSize = 38.sp
                                )
                            )

                            Spacer(modifier = Modifier.height(12.dp))

                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.Center,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                IconButton(
                                    onClick = onDecrementWater,
                                    colors = IconButtonDefaults.iconButtonColors(containerColor = SurfaceWhite)
                                ) {
                                    Icon(Icons.Default.Remove, contentDescription = "Minus water", tint = InfoBlue)
                                }

                                Spacer(modifier = Modifier.width(16.dp))

                                Button(
                                    onClick = onIncrementWater,
                                    colors = ButtonDefaults.buttonColors(containerColor = InfoBlue),
                                    shape = RoundedCornerShape(20.dp)
                                ) {
                                    Text("+ Drink 1 Glass")
                                }
                            }
                        }
                    }
                }

                // Superfoods Section
                item {
                    Text(
                        text = "Trimester 2 Superfoods",
                        style = MaterialTheme.typography.titleMedium.copy(
                            fontWeight = FontWeight.Bold,
                            color = TextPrimary
                        )
                    )
                }

                item {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        FoodChip(icon = "🥑", name = "Avocado", benefit = "Healthy fats", modifier = Modifier.weight(1f))
                        FoodChip(icon = "🥚", name = "Eggs", benefit = "Choline", modifier = Modifier.weight(1f))
                        FoodChip(icon = "🥬", name = "Spinach", benefit = "Folate & Iron", modifier = Modifier.weight(1f))
                    }
                }

                // Meal Recipes
                item {
                    Text(
                        text = "Nourishing Maternal Recipes",
                        style = MaterialTheme.typography.titleMedium.copy(
                            fontWeight = FontWeight.Bold,
                            color = TextPrimary
                        )
                    )
                }

                items(recipes, key = { it.id }) { recipe ->
                    RecipeCard(recipe = recipe)
                }
            }
        }
    }
}

@Composable
private fun FoodChip(
    icon: String,
    name: String,
    benefit: String,
    modifier: Modifier = Modifier
) {
    Surface(
        color = SurfaceWhite,
        shape = RoundedCornerShape(16.dp),
        border = androidx.compose.foundation.BorderStroke(1.dp, SoftPink),
        modifier = modifier
    ) {
        Column(
            modifier = Modifier.padding(10.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(text = icon, fontSize = 24.sp)
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = name,
                style = MaterialTheme.typography.labelMedium.copy(
                    fontWeight = FontWeight.Bold,
                    color = TextPrimary,
                    fontSize = 12.sp
                )
            )
            Text(
                text = benefit,
                style = MaterialTheme.typography.labelSmall.copy(
                    color = TextSecondary,
                    fontSize = 10.sp
                )
            )
        }
    }
}

@Composable
private fun RecipeCard(recipe: Recipe) {
    var expanded by remember { mutableStateOf(false) }

    Surface(
        color = SurfaceWhite,
        shape = RoundedCornerShape(20.dp),
        border = androidx.compose.foundation.BorderStroke(1.dp, SoftPink),
        shadowElevation = 2.dp,
        modifier = Modifier
            .fillMaxWidth()
            .clickable { expanded = !expanded }
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        text = recipe.title,
                        style = MaterialTheme.typography.titleMedium.copy(
                            fontWeight = FontWeight.Bold,
                            color = TextPrimary,
                            fontSize = 15.sp
                        )
                    )
                    Text(
                        text = "${recipe.category} · ${recipe.prepTime} · ${recipe.calories}",
                        style = MaterialTheme.typography.bodySmall.copy(
                            color = PrimaryPinkDark,
                            fontSize = 11.sp
                        )
                    )
                }

                Icon(
                    imageVector = if (expanded) Icons.Default.ExpandLess else Icons.Default.ExpandMore,
                    contentDescription = null,
                    tint = PrimaryPink
                )
            }

            Spacer(modifier = Modifier.height(6.dp))
            Text(
                text = recipe.benefits,
                style = MaterialTheme.typography.bodySmall.copy(
                    color = TextSecondary,
                    fontSize = 12.sp
                )
            )

            AnimatedVisibility(visible = expanded) {
                Column(modifier = Modifier.padding(top = 12.dp)) {
                    Text(
                        text = "Ingredients:",
                        style = MaterialTheme.typography.labelMedium.copy(
                            fontWeight = FontWeight.Bold,
                            color = TextPrimary
                        )
                    )
                    recipe.ingredients.forEach { item ->
                        Text(
                            text = "• $item",
                            style = MaterialTheme.typography.bodySmall.copy(
                                color = TextSecondary,
                                fontSize = 12.sp
                            ),
                            modifier = Modifier.padding(vertical = 1.dp)
                        )
                    }

                    Spacer(modifier = Modifier.height(8.dp))

                    Text(
                        text = "Instructions:",
                        style = MaterialTheme.typography.labelMedium.copy(
                            fontWeight = FontWeight.Bold,
                            color = TextPrimary
                        )
                    )
                    recipe.instructions.forEachIndexed { i, step ->
                        Text(
                            text = "${i + 1}. $step",
                            style = MaterialTheme.typography.bodySmall.copy(
                                color = TextSecondary,
                                fontSize = 12.sp
                            ),
                            modifier = Modifier.padding(vertical = 1.dp)
                        )
                    }
                }
            }
        }
    }
}
