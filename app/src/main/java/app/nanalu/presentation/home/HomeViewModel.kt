package app.nanalu.presentation.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import app.nanalu.domain.age.AgeCalculator
import app.nanalu.domain.repository.ChildProfileRepository
import java.time.Clock
import java.time.LocalDate
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn

class HomeViewModel(
    repository: ChildProfileRepository,
    private val clock: Clock = Clock.systemDefaultZone(),
) : ViewModel() {

    val uiState: StateFlow<HomeUiState> = repository.observeProfile()
        .map { profile ->
            HomeUiState(
                isLoading = false,
                child = profile?.let {
                    ChildSummary(it.name, AgeCalculator.calculate(it.birthDate, LocalDate.now(clock)))
                },
            )
        }
        // Falha de leitura nunca vira "sem cadastro": o estado de erro é explícito.
        .catch { emit(HomeUiState(isLoading = false, loadFailed = true)) }
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5_000), HomeUiState())
}
