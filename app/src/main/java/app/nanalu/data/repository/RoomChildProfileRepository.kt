package app.nanalu.data.repository

import app.nanalu.data.local.dao.ChildProfileDao
import app.nanalu.data.local.entity.ChildProfileEntity
import app.nanalu.domain.model.ChildProfile
import app.nanalu.domain.repository.ChildProfileRepository
import app.nanalu.domain.repository.CreateProfileResult
import java.time.Instant
import java.time.LocalDate
import java.util.UUID
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class RoomChildProfileRepository(
    private val dao: ChildProfileDao,
    private val nowMillis: () -> Long = System::currentTimeMillis,
    private val newId: () -> String = { UUID.randomUUID().toString() },
) : ChildProfileRepository {

    override fun observeProfile(): Flow<ChildProfile?> = dao.observeFirst().map { it?.toDomain() }

    override suspend fun create(name: String?, birthDate: LocalDate): CreateProfileResult {
        val now = nowMillis()
        val entity = ChildProfileEntity(newId(), name, birthDate.toEpochDay(), now, now)
        return if (dao.insertIfNone(entity)) CreateProfileResult.CREATED else CreateProfileResult.ALREADY_EXISTS
    }

    override suspend fun update(id: String, name: String?, birthDate: LocalDate): Boolean =
        dao.update(id, name, birthDate.toEpochDay(), nowMillis()) > 0

    override suspend fun delete(id: String): Boolean = dao.deleteById(id) > 0
}

internal fun ChildProfileEntity.toDomain() = ChildProfile(
    id = id,
    name = name,
    birthDate = LocalDate.ofEpochDay(birthDateEpochDay),
    createdAt = Instant.ofEpochMilli(createdAtEpochMs),
    updatedAt = Instant.ofEpochMilli(updatedAtEpochMs),
)
