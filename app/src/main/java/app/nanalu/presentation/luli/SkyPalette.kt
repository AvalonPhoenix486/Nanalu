package app.nanalu.presentation.luli

import androidx.compose.runtime.Immutable
import androidx.compose.ui.graphics.Color

@Immutable
class SkyPalette(
    val top: Color,
    val bottom: Color,
    val moon: Color,
    val moonShade: Color,
    val glow: Color,
    val card: Color,
    val onSky: Color,
    val onSkyMuted: Color,
    val isDark: Boolean,
)

private val NightPalette = SkyPalette(
    top = Color(0xFF141A3A), bottom = Color(0xFF2E2F6B),
    moon = Color(0xFFF7E8A9), moonShade = Color(0xFFE6D48B), glow = Color(0xFFB9B4FF),
    card = Color.White.copy(alpha = 0.12f),
    onSky = Color(0xFFF4F1FF), onSkyMuted = Color(0xFFC9C6EA), isDark = true,
)

private val DawnPalette = SkyPalette(
    top = Color(0xFFA899D8), bottom = Color(0xFFFFD9BD),
    moon = Color(0xFFFFF0D6), moonShade = Color(0xFFF0CDA8), glow = Color.White,
    card = Color.White.copy(alpha = 0.55f),
    onSky = Color(0xFF2B2250), onSkyMuted = Color(0xFF4F4272), isDark = false,
)

private val DayPalette = SkyPalette(
    top = Color(0xFFA9D8F0), bottom = Color(0xFFE8F5FB),
    moon = Color(0xFFFFFBEA), moonShade = Color(0xFFE8DFB8), glow = Color.White,
    card = Color.White.copy(alpha = 0.6f),
    onSky = Color(0xFF16324A), onSkyMuted = Color(0xFF42596B), isDark = false,
)

fun skyPalette(period: DayPeriod): SkyPalette = when (period) {
    DayPeriod.NIGHT -> NightPalette
    DayPeriod.DAWN -> DawnPalette
    DayPeriod.DAY -> DayPalette
}
