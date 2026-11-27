package app.nanalu.domain.repository

import app.nanalu.domain.model.ChildProfile
import kotlinx.coroutines.flow.Flow

interface ChildProfileRepository {
    /** Emite o perfil cadastrado, ou null quando não há nenhum. */
    fun observeProfile(): Flow<ChildProfile?>
}
