package per.nonobeam.tomapo.app.tomapo

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.offset
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.foundation.layout.size
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.clipRect
import androidx.compose.ui.graphics.drawscope.withTransform
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

@Composable
fun Tomato(isSplit: Boolean, offset: Dp) {
    val sliceCount = 5
    val tomatoSize = 120.dp
    val sliceHeightPx = with(LocalDensity.current) { (tomatoSize / sliceCount).toPx() }
    val spacingPx = with(LocalDensity.current) { if (isSplit) 12.dp.toPx() else 0f }

    Box(
        modifier = Modifier
            .offset(y = offset)
            .size(tomatoSize)
    ) {
        repeat(sliceCount) { index ->
            Canvas(
                modifier = Modifier
                    .matchParentSize()
                    .offset(y = with(LocalDensity.current) {
                        if (isSplit) ((index - 2) * spacingPx).toDp() else 0.dp
                    })
            ) {
                val sliceTop = index * sliceHeightPx
                clipRect(top = sliceTop, bottom = sliceTop + sliceHeightPx) {
                    drawCircle(Color(0xFFE74C3C), radius = size.minDimension / 2)
                }

                val centerX = size.width / 2

                when (index) {
                    0 -> {
                        val armY = sliceTop + sliceHeightPx * 0.8f
                        drawPath(
                            path = Path().apply {
                                moveTo(50f, armY)
                                cubicTo(20f - 50f, armY + 40f, 20f - 100f, armY + 40f, 20f - 150f, armY)
                            },
                            color = Color.Black,
                            style = Stroke(width = 8f)
                        )
                        drawPath(
                            path = Path().apply {
                                moveTo(size.width - 50f, armY)
                                cubicTo(size.width - 20f + 50f, armY + 40f, size.width - 20f + 100f, armY + 40f, size.width - 20f + 150f, armY)
                            },
                            color = Color.Black,
                            style = Stroke(width = 8f)
                        )
                    }
                    1 -> {
                        val eyeY = sliceTop + sliceHeightPx * 0.5f
                        drawCircle(Color.Black, radius = 4f, center = Offset(centerX - 15, eyeY))
                        drawCircle(Color.Black, radius = 4f, center = Offset(centerX + 15, eyeY))
                    }
                    2 -> {
                        val mouthY = sliceTop + sliceHeightPx * 0.7f
                        drawArc(
                            color = Color.Black,
                            startAngle = 0f,
                            sweepAngle = 180f,
                            useCenter = false,
                            topLeft = Offset(centerX - 35, mouthY),
                            size = Size(70f, 15f)
                        )
                    }
                    4 -> {
                        val legY = sliceTop + sliceHeightPx * 0.2f
                        drawPath(
                            path = Path().apply {
                                moveTo(50f, legY)
                                cubicTo(30f - 50f, legY + 30f, 30f - 100f, legY + 60f, 30f - 150f, legY + 60f)
                            },
                            color = Color.Black,
                            style = Stroke(width = 8f)
                        )
                        drawPath(
                            path = Path().apply {
                                moveTo(size.width - 50f, legY)
                                cubicTo(size.width - 30f + 50f, legY + 30f, size.width - 30f + 100f, legY + 60f, size.width - 30f + 150f, legY + 60f)
                            },
                            color = Color.Black,
                            style = Stroke(width = 8f)
                        )
                    }
                }
            }
        }
    }
}

fun androidx.compose.ui.graphics.drawscope.DrawScope.drawTriangle(rotationAngle: Float) {
    val path = Path().apply {
        moveTo(size.width / 2f, 0f)
        lineTo(0f, size.height)
        lineTo(size.width, size.height)
        close()
    }

    withTransform({
        rotate(rotationAngle)
    }) {
        drawPath(path, color = Color.White)
    }
}
