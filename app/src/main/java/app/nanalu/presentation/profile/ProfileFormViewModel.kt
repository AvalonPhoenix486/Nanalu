package app.nanalu.presentation.profile

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import app.nanalu.domain.profile.ProfileFieldError
import app.nanalu.domain.profile.ProfileValidator
import app.nanalu.domain.repository.ChildProfileRepository
import app.nanalu.domain.repository.CreateProfileResult
import java.time.Clock
import java.time.LocalDate
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

enum class SaveError { FAILED, ALREADY_EXISTS, NOT_FOUND }

data class ProfileFormUiState(
    val isEditing: Boolean,
    val isLoading: Boolean,
    val loadFailed: Boolean = false,
    val profileMissing: Boolean = false,
    val name: String = "",
    val birthDate: LocalDate? = null,
    val nameError: ProfileFieldError? = null,
    val birthDateError: ProfileFieldError? = null,
    val isSaving: Boolean = false,
    val saveError: SaveError? = null,
    val saved: Boolean = false,
    val isDirty: Boolean = false,
) {
    val canEdit: Boolean get() = !isLoading && !loadFailed && !profileMissing
}

/** Formulário de criação (isEditing = false) e de edição (true) do perfil infantil. */
class ProfileFormViewModel(
    private val repository: ChildProfileRepository,
    private val isEditing: Boolean,
    private val clock: Clock = Clock.systemDefaultZone(),
) : ViewModel() {

    private val _uiState = MutableStateFlow(ProfileFormUiState(isEditing = isEditing, isLoading = isEditing))
    val uiState: StateFlow<ProfileFormUiState> = _uiState.asStateFlow()

    private var profileId: String? = null
    private var initialName = ""
    private var initialBirthDate: LocalDate? = null

    init {
        if (isEditing) loadExisting()
    }

    private fun loadExisting() {
        viewModelScope.launch {
            try {
                val profile = repository.observeProfile().first()
                if (profile == null) {
                    _uiState.update { it.copy(isLoading = false, profileMissing = true) }
                } else {
                    profileId = profile.id
                    initialName = profile.name.orEmpty()
                    initialBirthDate = profile.birthDate
                    _uiState.update {
                        it.copy(isLoading = false, name = initialName, birthDate = initialBirthDate)
                    }
                }
            } catch (e: CancellationException) {
                throw e
            } catch (e: Exception) {
                _uiState.update { it.copy(isLoading = false, loadFailed = true) }
            }
        }
    }

    fun onNameChange(value: String) = edit { it.copy(name = value, nameError = null) }

    fun onBirthDateChange(value: LocalDate) = edit { it.copy(birthDate = value, birthDateError = null) }

    private fun edit(change: (ProfileFormUiState) -> ProfileFormUiState) {
        _uiState.update { current ->
            if (!current.canEdit || current.isSaving) return@update current
            val next = change(current).copy(saveError = null)
            next.copy(isDirty = next.name != initialName || next.birthDate != initialBirthDate)
        }
    }

    fun save() {
        val state = _uiState.value
        if (!state.canEdit || state.isSaving || state.saved) return

        val validation = ProfileValidator.validate(state.name, state.birthDate, LocalDate.now(clock))
        if (!validation.isValid) {
            _uiState.update { it.copy(nameError = validation.nameError, birthDateError = validation.birthDateError) }
            return
        }
        val birthDate = state.birthDate ?: return
        val name = ProfileValidator.normalizeName(state.name)

        _uiState.update { it.copy(isSaving = true, saveError = null, nameError = null, birthDateError = null) }
        viewModelScope.launch {
            val error: SaveError? = try {
                if (isEditing) {
                    val id = profileId
                    if (id != null && repository.update(id, name, birthDate)) null else SaveError.NOT_FOUND
                } else {
                    when (repository.create(name, birthDate)) {
                        CreateProfileResult.CREATED -> null
                        CreateProfileResult.ALREADY_EXISTS -> SaveError.ALREADY_EXISTS
                    }
                }
            } catch (e: CancellationException) {
                throw e
            } catch (e: Exception) {
                SaveError.FAILED
            }
            _uiState.update { it.copy(isSaving = false, saveError = error, saved = error == null) }
        }
    }
}
