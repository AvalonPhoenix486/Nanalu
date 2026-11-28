package app.nanalu

import app.nanalu.domain.model.ChildProfile
import app.nanalu.domain.repository.ChildProfileRepository
import app.nanalu.domain.repository.CreateProfileResult
import java.time.Instant
import java.time.LocalDate
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.flow

/** Fake em memória com as mesmas regras do repositório real: um único perfil. */
class FakeChildProfileRepository(initial: ChildProfile? = null) : ChildProfileRepository {
    val current = MutableStateFlow(initial)
    var observeFailure: Exception? = null
    var writeFailure: Exception? = null
    var createCalls = 0
    var updateCalls = 0
    var deleteCalls = 0

    override fun observeProfile(): Flow<ChildProfile?> {
        val failure = observeFailure
        return if (failure != null) flow { throw failure } else current
    }

    override suspend fun create(name: String?, birthDate: LocalDate): CreateProfileResult {
        createCalls++
        writeFailure?.let { throw it }
        if (current.value != null) return CreateProfileResult.ALREADY_EXISTS
        current.value = ChildProfile("id-1", name, birthDate, Instant.EPOCH, Instant.EPOCH)
        return CreateProfileResult.CREATED
    }

    override suspend fun update(id: String, name: String?, birthDate: LocalDate): Boolean {
        updateCalls++
        writeFailure?.let { throw it }
        val existing = current.value?.takeIf { it.id == id } ?: return false
        current.value = existing.copy(name = name, birthDate = birthDate, updatedAt = Instant.ofEpochSecond(1))
        return true
    }

    override suspend fun delete(id: String): Boolean {
        deleteCalls++
        writeFailure?.let { throw it }
        if (current.value?.id != id) return false
        current.value = null
        return true
    }
}

fun sampleProfile(name: String? = "Luna", birth: String = "2026-08-15") = ChildProfile(
    id = "id-1", name = name, birthDate = LocalDate.parse(birth), createdAt = Instant.EPOCH, updatedAt = Instant.EPOCH,
)
