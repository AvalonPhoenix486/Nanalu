package app.nanalu.presentation.luli

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import app.nanalu.R

/**
 * Luli, a mascote: uma lua cuja expressão acompanha o período do dia.
 * Desenho provisório até a referência visual oficial ser definida.
 */
@Composable
fun LuliMoon(period: DayPeriod, palette: SkyPalette, modifier: Modifier = Modifier, size: Dp = 220.dp) {
    val description = stringResource(
        when (period) {
            DayPeriod.NIGHT -> R.string.luli_description_night
            DayPeriod.DAWN -> R.string.luli_description_dawn
            DayPeriod.DAY -> R.string.luli_description_day
        },
    )
    Canvas(modifier.size(size).semantics { contentDescription = description }) {
        val c = center
        val glowR = this.size.minDimension / 2f
        val r = glowR * 0.72f
        val ink = Color(0xFF3B3A6B)

        drawCircle(
            brush = Brush.radialGradient(listOf(palette.glow.copy(alpha = 0.45f), Color.Transparent), center = c, radius = glowR),
            radius = glowR,
            center = c,
        )
        drawCircle(palette.moon, r, c)
        drawCircle(palette.moonShade, r, c, style = Stroke(width = r * 0.04f))

        val crater = palette.moonShade.copy(alpha = 0.6f)
        drawCircle(crater, r * 0.12f, c + Offset(r * 0.5f, -r * 0.55f))
        drawCircle(crater, r * 0.08f, c + Offset(-r * 0.62f, -r * 0.4f))
        drawCircle(crater, r * 0.10f, c + Offset(r * 0.55f, r * 0.55f))

        val cheek = Color(0xFFF2A0B5).copy(alpha = 0.5f)
        drawCircle(cheek, r * 0.13f, c + Offset(-r * 0.5f, r * 0.22f))
        drawCircle(cheek, r * 0.13f, c + Offset(r * 0.5f, r * 0.22f))

        val eyeY = c.y - r * 0.04f
        val eyeDx = r * 0.34f
        val eyeW = r * 0.22f
        val line = r * 0.05f
        for (side in listOf(-1f, 1f)) {
            val x = c.x + side * eyeDx
            when (period) {
                DayPeriod.NIGHT -> drawArc(
                    color = ink, startAngle = 0f, sweepAngle = 180f, useCenter = false,
                    topLeft = Offset(x - eyeW / 2, eyeY - eyeW / 4), size = Size(eyeW, eyeW / 2),
                    style = Stroke(width = line, cap = StrokeCap.Round),
                )
                DayPeriod.DAWN -> {
                    drawArc(
                        color = ink, startAngle = 0f, sweepAngle = 180f, useCenter = true,
                        topLeft = Offset(x - eyeW / 2, eyeY - eyeW / 2), size = Size(eyeW, eyeW),
                    )
                    drawLine(ink, Offset(x - eyeW / 2, eyeY), Offset(x + eyeW / 2, eyeY), strokeWidth = line * 0.8f, cap = StrokeCap.Round)
                }
                DayPeriod.DAY -> {
                    drawCircle(ink, eyeW * 0.42f, Offset(x, eyeY))
                    drawCircle(Color.White, eyeW * 0.12f, Offset(x + eyeW * 0.14f, eyeY - eyeW * 0.14f))
                }
            }
        }

        val mouthY = c.y + r * 0.2f
        if (period == DayPeriod.NIGHT) {
            drawCircle(ink.copy(alpha = 0.8f), r * 0.045f, Offset(c.x, mouthY + r * 0.04f))
        } else {
            drawArc(
                color = ink, startAngle = 20f, sweepAngle = 140f, useCenter = false,
                topLeft = Offset(c.x - r * 0.14f, mouthY - r * 0.1f), size = Size(r * 0.28f, r * 0.2f),
                style = Stroke(width = r * 0.045f, cap = StrokeCap.Round),
            )
        }
    }
}

private data class Star(val x: Float, val y: Float, val radiusDp: Float)

private val Stars = listOf(
    Star(0.10f, 0.06f, 1.6f), Star(0.28f, 0.14f, 1.2f), Star(0.46f, 0.05f, 1.8f),
    Star(0.70f, 0.11f, 1.3f), Star(0.88f, 0.07f, 1.7f), Star(0.18f, 0.30f, 1.3f),
    Star(0.80f, 0.27f, 1.5f), Star(0.93f, 0.42f, 1.2f), Star(0.06f, 0.48f, 1.5f),
    Star(0.22f, 0.62f, 1.1f), Star(0.90f, 0.66f, 1.4f), Star(0.12f, 0.80f, 1.3f),
)

/** Céu do período: degradê, estrelas à noite e nuvens de dia. */
@Composable
fun SkyBackdrop(period: DayPeriod, palette: SkyPalette, modifier: Modifier = Modifier) {
    val density = LocalDensity.current
    Canvas(modifier.fillMaxSize()) {
        drawRect(Brush.verticalGradient(listOf(palette.top, palette.bottom)))
        when (period) {
            DayPeriod.NIGHT -> Stars.forEach {
                drawCircle(palette.onSky.copy(alpha = 0.8f), with(density) { it.radiusDp.dp.toPx() }, Offset(size.width * it.x, size.height * it.y))
            }
            DayPeriod.DAY -> {
                cloud(Offset(size.width * 0.06f, size.height * 0.12f), size.width * 0.30f)
                cloud(Offset(size.width * 0.62f, size.height * 0.30f), size.width * 0.26f)
            }
            DayPeriod.DAWN -> Unit
        }
    }
}

private fun androidx.compose.ui.graphics.drawscope.DrawScope.cloud(topLeft: Offset, width: Float) {
    val color = Color.White.copy(alpha = 0.7f)
    val h = width * 0.28f
    drawRoundRect(color, topLeft + Offset(0f, h * 0.5f), Size(width, h), androidx.compose.ui.geometry.CornerRadius(h / 2))
    drawCircle(color, h * 0.7f, topLeft + Offset(width * 0.32f, h * 0.5f))
    drawCircle(color, h * 0.55f, topLeft + Offset(width * 0.6f, h * 0.55f))
}
