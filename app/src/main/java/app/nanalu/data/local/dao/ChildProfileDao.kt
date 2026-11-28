package app.nanalu.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Transaction
import app.nanalu.data.local.entity.ChildProfileEntity
import kotlinx.coroutines.flow.Flow

@Dao
abstract class ChildProfileDao {
    @Insert
    abstract suspend fun insert(profile: ChildProfileEntity)

    @Query("SELECT COUNT(*) FROM child_profile")
    abstract suspend fun count(): Int

    /** Verificação e inserção na mesma transação: nunca cria um segundo perfil. */
    @Transaction
    open suspend fun insertIfNone(profile: ChildProfileEntity): Boolean {
        if (count() > 0) return false
        insert(profile)
        return true
    }

    @Query("SELECT * FROM child_profile ORDER BY createdAtEpochMs LIMIT 1")
    abstract fun observeFirst(): Flow<ChildProfileEntity?>

    @Query(
        "UPDATE child_profile SET name = :name, birthDateEpochDay = :birthDateEpochDay, " +
            "updatedAtEpochMs = :updatedAtEpochMs WHERE id = :id",
    )
    abstract suspend fun update(id: String, name: String?, birthDateEpochDay: Long, updatedAtEpochMs: Long): Int

    /** Os episódios do perfil são removidos pela chave estrangeira em cascata, na mesma operação. */
    @Query("DELETE FROM child_profile WHERE id = :id")
    abstract suspend fun deleteById(id: String): Int
}
