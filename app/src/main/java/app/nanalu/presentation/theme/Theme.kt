package app.nanalu.presentation.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Typography
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontFamily

// Paleta provisória: a identidade visual definitiva depende da referência escolhida pelo responsável (PROJECT_CHARTER §8).
private val LightColors = lightColorScheme(
    primary = Color(0xFF4B4F8F),
    onPrimary = Color.White,
    secondaryContainer = Color(0xFFE4E1FA),
    onSecondaryContainer = Color(0xFF26275A),
    background = Color(0xFFFBF9FF),
    onBackground = Color(0xFF1D1B2E),
    surface = Color(0xFFFBF9FF),
    onSurface = Color(0xFF1D1B2E),
    onSurfaceVariant = Color(0xFF4F4D63),
    surfaceContainer = Color(0xFFF1EEFA),
)

private val DarkColors = darkColorScheme(
    primary = Color(0xFFC3C3FF),
    onPrimary = Color(0xFF1B1F5E),
    secondaryContainer = Color(0xFF3A3C6E),
    onSecondaryContainer = Color(0xFFE0E0FF),
    background = Color(0xFF11122B),
    onBackground = Color(0xFFE6E3F8),
    surface = Color(0xFF11122B),
    onSurface = Color(0xFFE6E3F8),
    onSurfaceVariant = Color(0xFFC6C4DC),
    surfaceContainer = Color(0xFF1B1C3A),
)

private val NanaluTypography = Typography().let {
    it.copy(
        headlineMedium = it.headlineMedium.copy(fontFamily = FontFamily.Serif),
        headlineSmall = it.headlineSmall.copy(fontFamily = FontFamily.Serif),
    )
}

@Composable
fun NanaluTheme(darkTheme: Boolean = isSystemInDarkTheme(), content: @Composable () -> Unit) {
    MaterialTheme(
        colorScheme = if (darkTheme) DarkColors else LightColors,
        typography = NanaluTypography,
        content = content,
    )
}
