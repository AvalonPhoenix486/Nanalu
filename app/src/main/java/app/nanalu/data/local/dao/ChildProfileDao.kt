package app.nanalu.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import app.nanalu.data.local.entity.ChildProfileEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface ChildProfileDao {
    @Insert
    suspend fun insert(profile: ChildProfileEntity)

    @Query("SELECT * FROM child_profile ORDER BY createdAtEpochMs LIMIT 1")
    fun observeFirst(): Flow<ChildProfileEntity?>

    @Query("DELETE FROM child_profile WHERE id = :id")
    suspend fun deleteById(id: String)
}
