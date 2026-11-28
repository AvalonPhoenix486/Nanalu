package app.nanalu.domain.repository

import app.nanalu.domain.model.ChildProfile
import java.time.LocalDate
import kotlinx.coroutines.flow.Flow

enum class CreateProfileResult { CREATED, ALREADY_EXISTS }

/** Falhas de armazenamento são propagadas como exceção; resultados esperados são valores. */
interface ChildProfileRepository {
    /** Emite o perfil cadastrado, ou null quando não há nenhum. */
    fun observeProfile(): Flow<ChildProfile?>

    /** O MVP acompanha uma única criança: não cria se já existir um perfil. */
    suspend fun create(name: String?, birthDate: LocalDate): CreateProfileResult

    /** Retorna false se o perfil não existe. Preserva createdAt. */
    suspend fun update(id: String, name: String?, birthDate: LocalDate): Boolean

    /** Remove o perfil e, por cascata, seus episódios. Retorna false se não existia. */
    suspend fun delete(id: String): Boolean
}
