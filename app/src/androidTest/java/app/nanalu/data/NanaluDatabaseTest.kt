package app.nanalu.data

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import app.nanalu.data.local.NanaluDatabase
import app.nanalu.data.local.entity.ChildProfileEntity
import app.nanalu.data.local.entity.SleepEpisodeEntity
import app.nanalu.data.repository.RoomChildProfileRepository
import app.nanalu.domain.model.SleepClassification
import app.nanalu.domain.model.SleepEpisodeStatus
import app.nanalu.domain.model.SleepSource
import java.time.LocalDate
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Assert.assertNull
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

/** Exige dispositivo ou emulador (não roda no CI atual). */
@RunWith(AndroidJUnit4::class)
class NanaluDatabaseTest {
    private lateinit var db: NanaluDatabase

    @Before fun setUp() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        db = Room.inMemoryDatabaseBuilder(context, NanaluDatabase::class.java).build()
    }

    @After fun tearDown() = db.close()

    private fun child(id: String) = ChildProfileEntity(id, null, LocalDate.of(2026, 8, 15).toEpochDay(), 1L, 1L)

    private fun episode(id: String, childId: String) = SleepEpisodeEntity(
        id = id, childId = childId, startedAtEpochMs = 1_000L, endedAtEpochMs = 2_000L,
        wakeConfirmedAtEnd = null, status = SleepEpisodeStatus.COMPLETED,
        classification = SleepClassification.UNCLASSIFIED, source = SleepSource.MANUAL,
        createdAtEpochMs = 1L, updatedAtEpochMs = 1L,
    )

    @Test fun profileRoundTripThroughRepository() = runBlocking {
        db.childProfileDao().insert(child("c1"))
        val profile = RoomChildProfileRepository(db.childProfileDao()).observeProfile().first()
        assertNotNull(profile)
        assertNull(profile!!.name)
        assertEquals(LocalDate.of(2026, 8, 15), profile.birthDate)
    }

    @Test fun deletingProfileRemovesItsEpisodes() = runBlocking {
        db.childProfileDao().insert(child("c1"))
        db.sleepEpisodeDao().insert(episode("e1", "c1"))
        db.sleepEpisodeDao().insert(episode("e2", "c1"))
        assertEquals(2, db.sleepEpisodeDao().observeByChild("c1").first().size)

        db.childProfileDao().deleteById("c1")

        assertEquals(0, db.sleepEpisodeDao().observeByChild("c1").first().size)
    }

    @Test(expected = android.database.sqlite.SQLiteConstraintException::class)
    fun episodeWithoutExistingChildIsRejected(): Unit = runBlocking {
        db.sleepEpisodeDao().insert(episode("e1", "inexistente"))
    }
}
