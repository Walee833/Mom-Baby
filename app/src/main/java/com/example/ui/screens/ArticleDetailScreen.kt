package com.example.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Bookmark
import androidx.compose.material.icons.filled.BookmarkBorder
import androidx.compose.material.icons.filled.Share
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.model.Article
import com.example.ui.components.AppHeader
import com.example.ui.theme.*

@Composable
fun ArticleDetailScreen(
    article: Article?,
    onToggleBookmark: (String) -> Unit,
    onBack: () -> Unit,
    modifier: Modifier = Modifier
) {
    if (article == null) {
        Box(modifier = modifier.fillMaxSize())
        return
    }

    Surface(
        color = BackgroundLight,
        modifier = modifier.fillMaxSize()
    ) {
        Column(modifier = Modifier.fillMaxSize()) {
            AppHeader(
                title = article.category,
                onBackClick = onBack,
                rightIcon = if (article.isBookmarked) Icons.Default.Bookmark else Icons.Default.BookmarkBorder,
                onRightIconClick = { onToggleBookmark(article.id) }
            )

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .verticalScroll(rememberScrollState())
                    .padding(horizontal = 22.dp, vertical = 12.dp)
            ) {
                Surface(
                    color = SoftPink,
                    shape = RoundedCornerShape(10.dp)
                ) {
                    Text(
                        text = "⏱ ${article.readTime}",
                        style = MaterialTheme.typography.labelSmall.copy(
                            color = PrimaryPinkDark,
                            fontWeight = FontWeight.Bold
                        ),
                        modifier = Modifier.padding(horizontal = 10.dp, vertical = 4.dp)
                    )
                }

                Spacer(modifier = Modifier.height(10.dp))

                Text(
                    text = article.title,
                    style = MaterialTheme.typography.headlineSmall.copy(
                        fontWeight = FontWeight.Bold,
                        color = TextPrimary,
                        lineHeight = 28.sp
                    )
                )

                Spacer(modifier = Modifier.height(16.dp))

                Surface(
                    color = SurfaceWhite,
                    shape = RoundedCornerShape(22.dp),
                    border = androidx.compose.foundation.BorderStroke(1.dp, SoftPink),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(
                        text = article.content,
                        style = MaterialTheme.typography.bodyLarge.copy(
                            color = TextPrimary,
                            lineHeight = 24.sp,
                            fontSize = 15.sp
                        ),
                        modifier = Modifier.padding(20.dp)
                    )
                }

                Spacer(modifier = Modifier.height(32.dp))
            }
        }
    }
}
