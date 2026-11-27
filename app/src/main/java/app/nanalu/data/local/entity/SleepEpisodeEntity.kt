package app.nanalu.data.local.entity

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey
import app.nanalu.domain.model.SleepClassification
import app.nanalu.domain.model.SleepEpisodeStatus
import app.nanalu.domain.model.SleepSource

/**
 * Episódio de sono (DATA_MODEL §5). A duração não é armazenada: é derivada de endedAt - startedAt.
 * CASCADE garante a exclusão efetiva dos episódios junto com o perfil (FR-004).
 */
@Entity(
    tableName = "sleep_episode",
    foreignKeys = [
        ForeignKey(
            entity = ChildProfileEntity::class,
            parentColumns = ["id"],
            childColumns = ["childId"],
            onDelete = ForeignKey.CASCADE,
        ),
    ],
    indices = [Index(value = ["childId", "startedAtEpochMs"])],
)
data class SleepEpisodeEntity(
    @PrimaryKey val id: String,
    val childId: String,
    val startedAtEpochMs: Long,
    val endedAtEpochMs: Long?,
    val wakeConfirmedAtEnd: Boolean?,
    val status: SleepEpisodeStatus,
    val classification: SleepClassification,
    val source: SleepSource,
    val createdAtEpochMs: Long,
    val updatedAtEpochMs: Long,
)
