package com.example.ui.components

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ui.theme.*

@Composable
fun GrowthLineChart(
    modifier: Modifier = Modifier
) {
    val labels = listOf("Birth", "3M", "6M", "9M", "12M")
    val weights = listOf(1.4f, 2.8f, 4.2f, 5.8f, 7.2f) // normalized values within 0-8kg range

    Surface(
        color = SurfaceWhite,
        shape = RoundedCornerShape(20.dp),
        modifier = modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = "Weight",
                    style = MaterialTheme.typography.titleMedium.copy(
                        fontWeight = FontWeight.Bold,
                        color = TextPrimary
                    )
                )
                Text(
                    text = "6.2 kg",
                    style = MaterialTheme.typography.titleMedium.copy(
                        fontWeight = FontWeight.Bold,
                        color = TextPrimary
                    )
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(180.dp)
            ) {
                Canvas(modifier = Modifier.fillMaxSize()) {
                    val width = size.width
                    val height = size.height
                    val paddingLeft = 32f
                    val paddingBottom = 30f
                    val chartWidth = width - paddingLeft - 20f
                    val chartHeight = height - paddingBottom - 10f

                    // Draw horizontal dashed/subtle grid lines & Y labels (0kg, 2kg, 4kg, 6kg, 8kg)
                    val ySteps = 4
                    for (i in 0..ySteps) {
                        val y = chartHeight - (i.toFloat() / ySteps.toFloat()) * chartHeight + 10f
                        drawLine(
                            color = BorderSoft,
                            start = Offset(paddingLeft, y),
                            end = Offset(width - 10f, y),
                            strokeWidth = 1.dp.toPx()
                        )
                    }

                    // Compute points
                    val points = mutableListOf<Offset>()
                    val stepX = chartWidth / (weights.size - 1)

                    weights.forEachIndexed { index, weight ->
                        val x = paddingLeft + index * stepX
                        val normalizedY = weight / 8f
                        val y = (chartHeight + 10f) - (normalizedY * chartHeight)
                        points.add(Offset(x, y))
                    }

                    // Create Filled Area Path
                    val fillPath = Path().apply {
                        moveTo(points.first().x, chartHeight + 10f)
                        lineTo(points.first().x, points.first().y)
                        for (i in 1 until points.size) {
                            val prev = points[i - 1]
                            val curr = points[i]
                            val midX = (prev.x + curr.x) / 2
                            cubicTo(midX, prev.y, midX, curr.y, curr.x, curr.y)
                        }
                        lineTo(points.last().x, chartHeight + 10f)
                        close()
                    }

                    drawPath(
                        path = fillPath,
                        brush = Brush.verticalGradient(
                            colors = listOf(
                                PrimaryPink.copy(alpha = 0.25f),
                                SoftPink.copy(alpha = 0.05f)
                            ),
                            startY = 10f,
                            endY = chartHeight + 10f
                        )
                    )

                    // Create Stroke Path
                    val strokePath = Path().apply {
                        moveTo(points.first().x, points.first().y)
                        for (i in 1 until points.size) {
                            val prev = points[i - 1]
                            val curr = points[i]
                            val midX = (prev.x + curr.x) / 2
                            cubicTo(midX, prev.y, midX, curr.y, curr.x, curr.y)
                        }
                    }

                    drawPath(
                        path = strokePath,
                        color = PrimaryPink,
                        style = Stroke(width = 3.dp.toPx(), cap = StrokeCap.Round)
                    )

                    // Draw dots
                    points.forEachIndexed { index, point ->
                        // Outer halo
                        drawCircle(
                            color = if (index == points.lastIndex) PrimaryPink.copy(alpha = 0.3f) else Color.Transparent,
                            radius = 8.dp.toPx(),
                            center = point
                        )
                        // Inner circle
                        drawCircle(
                            color = PrimaryPink,
                            radius = 4.dp.toPx(),
                            center = point
                        )
                    }
                }
            }

            // X-Axis labels
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 24.dp, end = 8.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                labels.forEach { label ->
                    Text(
                        text = label,
                        style = MaterialTheme.typography.labelSmall.copy(
                            fontSize = 11.sp,
                            color = TextSecondary
                        )
                    )
                }
            }
        }
    }
}
