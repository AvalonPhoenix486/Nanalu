package app.nanalu.presentation.components

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.lifecycle.repeatOnLifecycle
import app.nanalu.presentation.luli.DayPeriod
import java.time.LocalTime
import kotlinx.coroutines.delay

/** Período do dia atual, reavaliado a cada minuto apenas enquanto a tela está visível. */
@Composable
fun rememberDayPeriod(): State<DayPeriod> {
    val state = remember { mutableStateOf(DayPeriod.fromTime(LocalTime.now())) }
    val lifecycle = LocalLifecycleOwner.current.lifecycle
    LaunchedEffect(lifecycle) {
        lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
            while (true) {
                state.value = DayPeriod.fromTime(LocalTime.now())
                delay(60_000)
            }
        }
    }
    return state
}
