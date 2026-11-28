package app.nanalu.presentation.profile

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import app.nanalu.R
import app.nanalu.presentation.components.ageText
import app.nanalu.presentation.components.formatDate
import app.nanalu.presentation.components.rememberDayPeriod
import app.nanalu.presentation.luli.LuliMoon
import app.nanalu.presentation.luli.skyPalette

@Composable
fun ProfileRoute(
    viewModel: ProfileViewModel,
    onCreate: () -> Unit,
    onEdit: () -> Unit,
    onMessage: (String) -> Unit,
) {
    val state by viewModel.uiState.collectAsStateWithLifecycle()
    val deleted = stringResource(R.string.message_profile_deleted)
    val deleteFailed = stringResource(R.string.message_profile_delete_failed)
    LaunchedEffect(viewModel) {
        viewModel.events.collect { event ->
            onMessage(if (event == ProfileEvent.DELETED) deleted else deleteFailed)
        }
    }
    ProfileScreen(
        state = state,
        onCreate = onCreate,
        onEdit = onEdit,
        onDeleteRequest = viewModel::requestDelete,
        onDeleteConfirm = viewModel::confirmDelete,
        onDeleteDismiss = viewModel::dismissDelete,
    )
}

@Composable
fun ProfileScreen(
    state: ProfileUiState,
    onCreate: () -> Unit,
    onEdit: () -> Unit,
    onDeleteRequest: () -> Unit,
    onDeleteConfirm: () -> Unit,
    onDeleteDismiss: () -> Unit,
) {
    val period by rememberDayPeriod()
    Surface(Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background) {
        Column(
            Modifier.fillMaxSize().statusBarsPadding().verticalScroll(rememberScrollState()).padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            LuliMoon(period, skyPalette(period), size = 120.dp)
            Spacer(Modifier.height(16.dp))
            Text(stringResource(R.string.nav_profile), style = MaterialTheme.typography.headlineSmall)
            Spacer(Modifier.height(16.dp))

            when {
                state.isLoading -> CircularProgressIndicator()
                state.loadFailed -> Text(
                    stringResource(R.string.home_error_title) + ". " + stringResource(R.string.home_error_body),
                    textAlign = TextAlign.Center,
                    color = MaterialTheme.colorScheme.error,
                )
                state.profile == null -> EmptyProfile(onCreate)
                else -> ProfileDetailsCard(state, onEdit, onDeleteRequest)
            }

            Spacer(Modifier.height(24.dp))
            Text(
                stringResource(R.string.profile_privacy_note),
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                textAlign = TextAlign.Center,
            )
        }
    }

    if (state.showDeleteConfirmation) {
        AlertDialog(
            onDismissRequest = onDeleteDismiss,
            title = { Text(stringResource(R.string.delete_title)) },
            text = { Text(stringResource(R.string.delete_body)) },
            confirmButton = {
                TextButton(onClick = onDeleteConfirm, enabled = !state.isDeleting) {
                    Text(
                        stringResource(if (state.isDeleting) R.string.delete_deleting else R.string.delete_confirm),
                        color = MaterialTheme.colorScheme.error,
                    )
                }
            },
            dismissButton = {
                TextButton(onClick = onDeleteDismiss, enabled = !state.isDeleting) {
                    Text(stringResource(R.string.action_cancel))
                }
            },
        )
    }
}

@Composable
private fun EmptyProfile(onCreate: () -> Unit) {
    Column(horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.spacedBy(8.dp)) {
        Text(stringResource(R.string.home_empty_title), style = MaterialTheme.typography.titleMedium)
        Text(
            stringResource(R.string.profile_empty_body),
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            textAlign = TextAlign.Center,
        )
        Spacer(Modifier.height(8.dp))
        Button(onClick = onCreate) { Text(stringResource(R.string.action_register_child)) }
    }
}

@Composable
private fun ProfileDetailsCard(state: ProfileUiState, onEdit: () -> Unit, onDeleteRequest: () -> Unit) {
    val profile = state.profile ?: return
    Surface(
        Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(24.dp),
        color = MaterialTheme.colorScheme.surfaceContainer,
    ) {
        Column(Modifier.padding(20.dp), verticalArrangement = Arrangement.spacedBy(12.dp)) {
            Text(
                profile.name ?: stringResource(R.string.home_profile_fallback_title),
                style = MaterialTheme.typography.titleLarge,
            )
            LabeledValue(stringResource(R.string.profile_birth_label), formatDate(profile.birthDate))
            profile.age?.let { LabeledValue(stringResource(R.string.profile_age_label), ageText(it)) }
        }
    }
    Spacer(Modifier.height(16.dp))
    Button(onClick = onEdit, Modifier.fillMaxWidth()) { Text(stringResource(R.string.action_edit_profile)) }
    OutlinedButton(onClick = onDeleteRequest, Modifier.fillMaxWidth().padding(top = 8.dp)) {
        Text(stringResource(R.string.action_delete_profile), color = MaterialTheme.colorScheme.error)
    }
}

@Composable
private fun LabeledValue(label: String, value: String) {
    Column {
        Text(label, style = MaterialTheme.typography.labelMedium, color = MaterialTheme.colorScheme.onSurfaceVariant)
        Text(value, style = MaterialTheme.typography.bodyLarge)
    }
}
