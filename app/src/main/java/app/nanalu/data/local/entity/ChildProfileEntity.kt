package app.nanalu.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

/** Datas como números: nascimento em epoch day, instantes em epoch millis (instante inequívoco). */
@Entity(tableName = "child_profile")
data class ChildProfileEntity(
    @PrimaryKey val id: String,
    val name: String?,
    val birthDateEpochDay: Long,
    val createdAtEpochMs: Long,
    val updatedAtEpochMs: Long,
)
