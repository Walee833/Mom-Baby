package com.example.ui.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Bookmark
import androidx.compose.material.icons.filled.BookmarkBorder
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.model.Article
import com.example.ui.components.AppHeader
import com.example.ui.components.SegmentedTabBar
import com.example.ui.theme.*

@Composable
fun HealthEducationScreen(
    articles: List<Article>,
    onSelectArticle: (Article) -> Unit,
    onToggleBookmark: (String) -> Unit,
    onBack: () -> Unit,
    modifier: Modifier = Modifier
) {
    var searchQuery by remember { mutableStateOf("") }
    var selectedCategoryIndex by remember { mutableIntStateOf(0) }
    val categories = listOf("All", "Labor", "Baby Care", "Nutrition")

    val filteredArticles = articles.filter { article ->
        val matchesCategory = when (selectedCategoryIndex) {
            1 -> article.category.contains("Labor", ignoreCase = true)
            2 -> article.category.contains("Baby", ignoreCase = true)
            3 -> article.category.contains("Nutrition", ignoreCase = true)
            else -> true
        }
        val matchesQuery = searchQuery.isBlank() ||
                article.title.contains(searchQuery, ignoreCase = true) ||
                article.summary.contains(searchQuery, ignoreCase = true)

        matchesCategory && matchesQuery
    }

    Surface(
        color = BackgroundLight,
        modifier = modifier.fillMaxSize()
    ) {
        Column(modifier = Modifier.fillMaxSize()) {
            AppHeader(
                title = "Health Education",
                onBackClick = onBack
            )

            // Search Box
            OutlinedTextField(
                value = searchQuery,
                onValueChange = { searchQuery = it },
                placeholder = { Text("Search maternal topics, symptoms, guides...") },
                leadingIcon = { Icon(Icons.Default.Search, contentDescription = "Search", tint = PrimaryPink) },
                shape = RoundedCornerShape(18.dp),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedContainerColor = SurfaceWhite,
                    unfocusedContainerColor = SurfaceWhite,
                    focusedBorderColor = PrimaryPink,
                    unfocusedBorderColor = SoftPink
                ),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp)
            )

            Spacer(modifier = Modifier.height(10.dp))

            SegmentedTabBar(
                tabs = categories,
                selectedIndex = selectedCategoryIndex,
                onTabSelected = { selectedCategoryIndex = it },
                modifier = Modifier.testTag("health_categories")
            )

            Spacer(modifier = Modifier.height(14.dp))

            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 20.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp),
                contentPadding = PaddingValues(bottom = 32.dp)
            ) {
                items(filteredArticles, key = { it.id }) { article ->
                    ArticleCard(
                        article = article,
                        onArticleClick = { onSelectArticle(article) },
                        onToggleBookmark = { onToggleBookmark(article.id) }
                    )
                }
            }
        }
    }
}

@Composable
private fun ArticleCard(
    article: Article,
    onArticleClick: () -> Unit,
    onToggleBookmark: () -> Unit
) {
    Surface(
        color = SurfaceWhite,
        shape = RoundedCornerShape(20.dp),
        border = androidx.compose.foundation.BorderStroke(1.dp, SoftPink),
        shadowElevation = 2.dp,
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onArticleClick)
            .testTag("article_card_${article.id}")
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Surface(
                    color = SoftPink,
                    shape = RoundedCornerShape(10.dp)
                ) {
                    Text(
                        text = article.category,
                        style = MaterialTheme.typography.labelSmall.copy(
                            color = PrimaryPinkDark,
                            fontWeight = FontWeight.Bold,
                            fontSize = 11.sp
                        ),
                        modifier = Modifier.padding(horizontal = 8.dp, vertical = 3.dp)
                    )
                }

                IconButton(
                    onClick = onToggleBookmark,
                    modifier = Modifier.size(24.dp)
                ) {
                    Icon(
                        imageVector = if (article.isBookmarked) Icons.Default.Bookmark else Icons.Default.BookmarkBorder,
                        contentDescription = "Bookmark",
                        tint = if (article.isBookmarked) PrimaryPink else TextSecondary
                    )
                }
            }

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = article.title,
                style = MaterialTheme.typography.titleMedium.copy(
                    fontWeight = FontWeight.Bold,
                    color = TextPrimary,
                    fontSize = 15.sp
                )
            )

            Spacer(modifier = Modifier.height(4.dp))

            Text(
                text = article.summary,
                style = MaterialTheme.typography.bodySmall.copy(
                    color = TextSecondary,
                    fontSize = 12.sp,
                    lineHeight = 17.sp
                )
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = "⏱ ${article.readTime}",
                style = MaterialTheme.typography.labelSmall.copy(
                    color = PrimaryPinkDark,
                    fontSize = 11.sp
                )
            )
        }
    }
}
