package com.mikyegresl.valostat.ui.widget

import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import kotlin.math.PI
import kotlin.math.cos
import kotlin.math.min
import kotlin.math.pow
import kotlin.math.sin
import kotlin.math.sqrt

fun gradientModifier(
    angle: Float,
    firstColor: Pair<Float, Color>,
    secondColor: Pair<Float, Color>
): Modifier {
    val bgModifier = Modifier.drawBehind {
        val angleRad = angle / 180f * PI
        val x = cos(angleRad).toFloat() //Fractional x
        val y = sin(angleRad).toFloat() //Fractional y

        val radius = sqrt(size.width.pow(2) + size.height.pow(2)) / 2f
        val offset = center + Offset(x * radius, y * radius)

        val exactOffset = Offset(
            x = min(offset.x.coerceAtLeast(0f), size.width),
            y = size.height - min(offset.y.coerceAtLeast(0f), size.height)
        )

        drawRect(
            brush = Brush.linearGradient(
                firstColor,
                secondColor,
                start = Offset(size.width, size.height) - exactOffset,
                end = exactOffset
            ),
            size = size
        )
    }
    return bgModifier
}