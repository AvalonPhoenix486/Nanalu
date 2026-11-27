package app.nanalu.data.repository

import app.nanalu.data.local.dao.ChildProfileDao
import app.nanalu.data.local.entity.ChildProfileEntity
import app.nanalu.domain.model.ChildProfile
import app.nanalu.domain.repository.ChildProfileRepository
import java.time.Instant
import java.time.LocalDate
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class RoomChildProfileRepository(private val dao: ChildProfileDao) : ChildProfileRepository {
    override fun observeProfile(): Flow<ChildProfile?> = dao.observeFirst().map { it?.toDomain() }
}

internal fun ChildProfileEntity.toDomain() = ChildProfile(
    id = id,
    name = name,
    birthDate = LocalDate.ofEpochDay(birthDateEpochDay),
    createdAt = Instant.ofEpochMilli(createdAtEpochMs),
    updatedAt = Instant.ofEpochMilli(updatedAtEpochMs),
)
