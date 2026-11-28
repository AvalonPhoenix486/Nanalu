package app.nanalu.presentation.home

import android.app.Activity
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.core.view.WindowCompat
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import app.nanalu.R
import app.nanalu.presentation.components.ageText
import app.nanalu.presentation.components.rememberDayPeriod
import app.nanalu.presentation.luli.DayPeriod
import app.nanalu.presentation.luli.LuliMoon
import app.nanalu.presentation.luli.SkyBackdrop
import app.nanalu.presentation.luli.SkyPalette
import app.nanalu.presentation.luli.skyPalette

@Composable
fun HomeRoute(viewModel: HomeViewModel, onCreateProfile: () -> Unit) {
    val state by viewModel.uiState.collectAsStateWithLifecycle()
    val period by rememberDayPeriod()
    HomeScreen(state, period, onCreateProfile)
}

@Composable
fun HomeScreen(state: HomeUiState, period: DayPeriod, onCreateProfile: () -> Unit) {
    val palette = skyPalette(period)
    StatusBarIcons(lightIcons = palette.isDark)

    Box(Modifier.fillMaxSize()) {
        SkyBackdrop(period, palette)
        Column(
            Modifier.fillMaxSize().statusBarsPadding().verticalScroll(rememberScrollState()).padding(horizontal = 24.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Spacer(Modifier.height(24.dp))
            Text(
                stringResource(
                    when (period) {
                        DayPeriod.NIGHT -> R.string.greeting_night
                        DayPeriod.DAWN -> R.string.greeting_dawn
                        DayPeriod.DAY -> R.string.greeting_day
                    },
                ),
                style = MaterialTheme.typography.headlineMedium,
                color = palette.onSky,
            )
            Spacer(Modifier.height(8.dp))
            Text(
                stringResource(R.string.home_tagline),
                style = MaterialTheme.typography.bodyMedium.copy(fontFamily = FontFamily.Serif),
                color = palette.onSkyMuted,
                textAlign = TextAlign.Center,
            )
            Spacer(Modifier.height(16.dp))
            LuliMoon(period, palette)
            Spacer(Modifier.height(16.dp))
            StatusCard(state, palette, onCreateProfile)
            Spacer(Modifier.height(16.dp))
            Text(
                stringResource(R.string.home_disclaimer),
                style = MaterialTheme.typography.bodySmall,
                color = palette.onSkyMuted,
                textAlign = TextAlign.Center,
            )
            Spacer(Modifier.height(24.dp))
        }
    }
}

@Composable
private fun StatusCard(state: HomeUiState, palette: SkyPalette, onCreateProfile: () -> Unit) {
    Surface(Modifier.fillMaxWidth(), shape = RoundedCornerShape(24.dp), color = palette.card) {
        Column(
            Modifier.padding(20.dp).fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(6.dp),
        ) {
            when {
                state.isLoading -> {
                    CircularProgressIndicator(Modifier.size(24.dp), color = palette.onSky, strokeWidth = 2.dp)
                    Text(stringResource(R.string.home_loading), color = palette.onSkyMuted)
                }
                state.loadFailed -> CardText(
                    stringResource(R.string.home_error_title), stringResource(R.string.home_error_body), palette,
                )
                state.child == null -> {
                    CardText(stringResource(R.string.home_empty_title), stringResource(R.string.home_empty_body), palette)
                    Button(onClick = onCreateProfile) { Text(stringResource(R.string.action_register_child)) }
                }
                else -> CardText(
                    state.child.name ?: stringResource(R.string.home_profile_fallback_title),
                    state.child.age?.let { ageText(it) },
                    palette,
                )
            }
        }
    }
}

@Composable
private fun CardText(title: String, body: String?, palette: SkyPalette) {
    Text(title, style = MaterialTheme.typography.titleMedium, color = palette.onSky, textAlign = TextAlign.Center)
    if (body != null) {
        Text(body, style = MaterialTheme.typography.bodyMedium, color = palette.onSkyMuted, textAlign = TextAlign.Center)
    }
}

/** Ajusta os ícones da barra de status ao céu desta tela e restaura o padrão do sistema ao sair. */
@Composable
private fun StatusBarIcons(lightIcons: Boolean) {
    val view = LocalView.current
    val systemDark = isSystemInDarkTheme()
    DisposableEffect(lightIcons, systemDark) {
        val window = (view.context as? Activity)?.window
        val controller = window?.let { WindowCompat.getInsetsController(it, view) }
        controller?.isAppearanceLightStatusBars = !lightIcons
        onDispose { controller?.isAppearanceLightStatusBars = !systemDark }
    }
}
