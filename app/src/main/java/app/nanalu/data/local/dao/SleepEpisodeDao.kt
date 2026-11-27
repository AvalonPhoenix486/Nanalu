package app.nanalu.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import app.nanalu.data.local.entity.SleepEpisodeEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface SleepEpisodeDao {
    @Insert
    suspend fun insert(episode: SleepEpisodeEntity)

    @Query("SELECT * FROM sleep_episode WHERE childId = :childId ORDER BY startedAtEpochMs DESC")
    fun observeByChild(childId: String): Flow<List<SleepEpisodeEntity>>

    @Query("DELETE FROM sleep_episode WHERE id = :id")
    suspend fun deleteById(id: String)
}
