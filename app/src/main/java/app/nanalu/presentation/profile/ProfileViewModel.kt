package app.nanalu.presentation.profile

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import app.nanalu.domain.age.Age
import app.nanalu.domain.age.AgeCalculator
import app.nanalu.domain.model.ChildProfile
import app.nanalu.domain.repository.ChildProfileRepository
import java.time.Clock
import java.time.LocalDate
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

data class ProfileDetails(val id: String, val name: String?, val birthDate: LocalDate, val age: Age?)

data class ProfileUiState(
    val isLoading: Boolean = true,
    val loadFailed: Boolean = false,
    val profile: ProfileDetails? = null,
    val showDeleteConfirmation: Boolean = false,
    val isDeleting: Boolean = false,
)

enum class ProfileEvent { DELETED, DELETE_FAILED }

class ProfileViewModel(
    private val repository: ChildProfileRepository,
    private val clock: Clock = Clock.systemDefaultZone(),
) : ViewModel() {

    private sealed interface Source {
        data object Loading : Source
        data class Loaded(val profile: ChildProfile?) : Source
        data object Failed : Source
    }

    private data class DeleteState(val showConfirmation: Boolean = false, val isDeleting: Boolean = false)

    private val deleteState = MutableStateFlow(DeleteState())
    private val _events = Channel<ProfileEvent>(Channel.BUFFERED)
    val events: Flow<ProfileEvent> = _events.receiveAsFlow()

    val uiState: StateFlow<ProfileUiState> = combine(
        repository.observeProfile()
            .map<ChildProfile?, Source> { Source.Loaded(it) }
            .catch { emit(Source.Failed) }
            .onStart { emit(Source.Loading) },
        deleteState,
    ) { source, delete ->
        val base = ProfileUiState(
            showDeleteConfirmation = delete.showConfirmation,
            isDeleting = delete.isDeleting,
        )
        when (source) {
            Source.Loading -> base
            Source.Failed -> base.copy(isLoading = false, loadFailed = true)
            is Source.Loaded -> base.copy(
                isLoading = false,
                profile = source.profile?.let {
                    ProfileDetails(it.id, it.name, it.birthDate, AgeCalculator.calculate(it.birthDate, LocalDate.now(clock)))
                },
            )
        }
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5_000), ProfileUiState())

    fun requestDelete() {
        if (uiState.value.profile != null) deleteState.update { it.copy(showConfirmation = true) }
    }

    fun dismissDelete() {
        if (!deleteState.value.isDeleting) deleteState.value = DeleteState()
    }

    fun confirmDelete() {
        val id = uiState.value.profile?.id ?: return
        if (deleteState.value.isDeleting) return
        deleteState.value = DeleteState(showConfirmation = true, isDeleting = true)
        viewModelScope.launch {
            try {
                repository.delete(id)
                _events.send(ProfileEvent.DELETED)
            } catch (e: CancellationException) {
                throw e
            } catch (e: Exception) {
                _events.send(ProfileEvent.DELETE_FAILED)
            } finally {
                deleteState.value = DeleteState()
            }
        }
    }
}
