package app.nanalu.presentation.profile

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SelectableDates
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import app.nanalu.R
import app.nanalu.domain.profile.ProfileFieldError
import app.nanalu.domain.profile.ProfileValidator
import app.nanalu.presentation.components.formatDate
import java.time.Instant
import java.time.LocalDate
import java.time.ZoneOffset

@Composable
fun ProfileFormRoute(viewModel: ProfileFormViewModel, onClose: () -> Unit, onSaved: (wasEditing: Boolean) -> Unit) {
    val state by viewModel.uiState.collectAsStateWithLifecycle()
    LaunchedEffect(state.saved) {
        if (state.saved) onSaved(state.isEditing)
    }
    ProfileFormScreen(
        state = state,
        onNameChange = viewModel::onNameChange,
        onBirthDateChange = viewModel::onBirthDateChange,
        onSave = viewModel::save,
        onClose = onClose,
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfileFormScreen(
    state: ProfileFormUiState,
    onNameChange: (String) -> Unit,
    onBirthDateChange: (LocalDate) -> Unit,
    onSave: () -> Unit,
    onClose: () -> Unit,
) {
    var showDiscardDialog by remember { mutableStateOf(false) }
    val requestClose = { if (state.isDirty && !state.isSaving) showDiscardDialog = true else onClose() }
    BackHandler(onBack = requestClose)

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(stringResource(if (state.isEditing) R.string.form_title_edit else R.string.form_title_create))
                },
                navigationIcon = {
                    IconButton(onClick = requestClose) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = stringResource(R.string.action_back))
                    }
                },
            )
        },
    ) { inner ->
        Box(Modifier.fillMaxSize().padding(inner).imePadding()) {
            when {
                state.isLoading -> CircularProgressIndicator(Modifier.align(Alignment.Center))
                state.loadFailed -> Message(stringResource(R.string.form_load_failed))
                state.profileMissing -> Message(stringResource(R.string.form_profile_missing))
                else -> FormFields(state, onNameChange, onBirthDateChange, onSave)
            }
        }
    }

    if (showDiscardDialog) {
        AlertDialog(
            onDismissRequest = { showDiscardDialog = false },
            title = { Text(stringResource(R.string.discard_title)) },
            text = { Text(stringResource(R.string.discard_body)) },
            confirmButton = {
                TextButton(onClick = { showDiscardDialog = false; onClose() }) {
                    Text(stringResource(R.string.discard_confirm))
                }
            },
            dismissButton = {
                TextButton(onClick = { showDiscardDialog = false }) { Text(stringResource(R.string.discard_keep)) }
            },
        )
    }
}

@Composable
private fun Message(text: String) {
    Text(text, Modifier.padding(24.dp), style = MaterialTheme.typography.bodyLarge)
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun FormFields(
    state: ProfileFormUiState,
    onNameChange: (String) -> Unit,
    onBirthDateChange: (LocalDate) -> Unit,
    onSave: () -> Unit,
) {
    var showPicker by remember { mutableStateOf(false) }
    val today = remember { LocalDate.now() }

    Column(
        Modifier.fillMaxSize().verticalScroll(rememberScrollState()).padding(24.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp),
    ) {
        OutlinedTextField(
            value = state.name,
            onValueChange = onNameChange,
            modifier = Modifier.fillMaxWidth(),
            label = { Text(stringResource(R.string.field_name_label)) },
            singleLine = true,
            isError = state.nameError != null,
            supportingText = { Text(errorText(state.nameError) ?: stringResource(R.string.field_name_hint)) },
            keyboardOptions = KeyboardOptions(capitalization = KeyboardCapitalization.Words),
        )

        Box {
            OutlinedTextField(
                value = state.birthDate?.let { formatDate(it) }.orEmpty(),
                onValueChange = {},
                modifier = Modifier.fillMaxWidth(),
                readOnly = true,
                label = { Text(stringResource(R.string.field_birth_label)) },
                placeholder = { Text(stringResource(R.string.field_birth_placeholder)) },
                trailingIcon = { Icon(Icons.Filled.DateRange, contentDescription = null) },
                isError = state.birthDateError != null,
                supportingText = errorText(state.birthDateError)?.let { message -> { Text(message) } },
            )
            // Camada de toque: um campo somente leitura não recebe cliques por conta própria.
            val openPicker = stringResource(R.string.field_birth_open_picker)
            Box(Modifier.matchParentSize().clickable(onClickLabel = openPicker) { showPicker = true })
        }

        state.saveError?.let {
            Text(
                stringResource(
                    when (it) {
                        SaveError.FAILED -> R.string.save_error_failed
                        SaveError.ALREADY_EXISTS -> R.string.save_error_exists
                        SaveError.NOT_FOUND -> R.string.save_error_not_found
                    },
                ),
                color = MaterialTheme.colorScheme.error,
                style = MaterialTheme.typography.bodyMedium,
            )
        }

        Button(onClick = onSave, enabled = !state.isSaving, modifier = Modifier.fillMaxWidth()) {
            Text(stringResource(if (state.isSaving) R.string.action_saving else R.string.action_save))
        }
    }

    if (showPicker) {
        val pickerState = rememberDatePickerState(
            initialSelectedDateMillis = state.birthDate?.toUtcMillis(),
            selectableDates = remember(today) {
                object : SelectableDates {
                    override fun isSelectableDate(utcTimeMillis: Long) = utcTimeMillis <= today.toUtcMillis()
                    override fun isSelectableYear(year: Int) = year <= today.year
                }
            },
        )
        DatePickerDialog(
            onDismissRequest = { showPicker = false },
            confirmButton = {
                TextButton(onClick = {
                    pickerState.selectedDateMillis?.let { onBirthDateChange(it.toUtcDate()) }
                    showPicker = false
                }) { Text(stringResource(R.string.action_ok)) }
            },
            dismissButton = { TextButton(onClick = { showPicker = false }) { Text(stringResource(R.string.action_cancel)) } },
        ) { DatePicker(state = pickerState) }
    }
}

@Composable
private fun errorText(error: ProfileFieldError?): String? = when (error) {
    null -> null
    ProfileFieldError.NAME_TOO_LONG -> stringResource(R.string.error_name_too_long, ProfileValidator.MAX_NAME_LENGTH)
    ProfileFieldError.NAME_INVALID_CHARACTERS -> stringResource(R.string.error_name_invalid)
    ProfileFieldError.BIRTH_DATE_MISSING -> stringResource(R.string.error_birth_missing)
    ProfileFieldError.BIRTH_DATE_IN_FUTURE -> stringResource(R.string.error_birth_future)
}

// O DatePicker do Material trabalha em milissegundos UTC à meia-noite.
private fun LocalDate.toUtcMillis(): Long = atStartOfDay(ZoneOffset.UTC).toInstant().toEpochMilli()

private fun Long.toUtcDate(): LocalDate = Instant.ofEpochMilli(this).atZone(ZoneOffset.UTC).toLocalDate()
