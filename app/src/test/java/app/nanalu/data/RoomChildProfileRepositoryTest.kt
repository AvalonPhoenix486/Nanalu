package app.nanalu.data

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import app.nanalu.data.local.NanaluDatabase
import app.nanalu.data.local.entity.SleepEpisodeEntity
import app.nanalu.data.repository.RoomChildProfileRepository
import app.nanalu.domain.model.SleepClassification
import app.nanalu.domain.model.SleepEpisodeStatus
import app.nanalu.domain.model.SleepSource
import app.nanalu.domain.repository.CreateProfileResult
import java.time.Instant
import java.time.LocalDate
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertNotNull
import org.junit.Assert.assertNull
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.annotation.Config

/** Room real (SQLite) em JVM via Robolectric: verifica persistência, regra de perfil único e cascata. */
@RunWith(AndroidJUnit4::class)
@Config(sdk = [34])
class RoomChildProfileRepositoryTest {
    private lateinit var db: NanaluDatabase
    private lateinit var repo: RoomChildProfileRepository
    private var now = 1_000L
    private var nextId = 0

    @Before fun setUp() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        db = Room.inMemoryDatabaseBuilder(context, NanaluDatabase::class.java).allowMainThreadQueries().build()
        repo = RoomChildProfileRepository(db.childProfileDao(), nowMillis = { now }, newId = { "id-${++nextId}" })
    }

    @After fun tearDown() = db.close()

    private val birth = LocalDate.of(2026, 8, 15)

    private fun episode(id: String, childId: String) = SleepEpisodeEntity(
        id = id, childId = childId, startedAtEpochMs = 1_000L, endedAtEpochMs = 2_000L,
        wakeConfirmedAtEnd = null, status = SleepEpisodeStatus.COMPLETED,
        classification = SleepClassification.UNCLASSIFIED, source = SleepSource.MANUAL,
        createdAtEpochMs = 1L, updatedAtEpochMs = 1L,
    )

    @Test fun emptyDatabaseEmitsNull() = runBlocking {
        assertNull(repo.observeProfile().first())
    }

    @Test fun createPersistsProfileWithOptionalName() = runBlocking {
        assertEquals(CreateProfileResult.CREATED, repo.create(null, birth))
        val profile = repo.observeProfile().first()!!
        assertNull(profile.name)
        assertEquals(birth, profile.birthDate)
        assertEquals(Instant.ofEpochMilli(1_000L), profile.createdAt)
        assertEquals(profile.createdAt, profile.updatedAt)
    }

    @Test fun secondCreateIsRejectedAndFirstIsKept() = runBlocking {
        repo.create("Luna", birth)
        assertEquals(CreateProfileResult.ALREADY_EXISTS, repo.create("Outra", LocalDate.of(2025, 1, 1)))
        val profile = repo.observeProfile().first()!!
        assertEquals("Luna", profile.name)
        assertEquals(1, db.childProfileDao().count())
    }

    @Test fun updateChangesFieldsKeepsCreatedAtAndBumpsUpdatedAt() = runBlocking {
        repo.create("Luna", birth)
        val id = repo.observeProfile().first()!!.id
        now = 5_000L
        assertTrue(repo.update(id, null, LocalDate.of(2026, 8, 10)))
        val profile = repo.observeProfile().first()!!
        assertNull(profile.name)
        assertEquals(LocalDate.of(2026, 8, 10), profile.birthDate)
        assertEquals(Instant.ofEpochMilli(1_000L), profile.createdAt)
        assertEquals(Instant.ofEpochMilli(5_000L), profile.updatedAt)
    }

    @Test fun updateKeepsSleepEpisodes() = runBlocking {
        repo.create("Luna", birth)
        val id = repo.observeProfile().first()!!.id
        db.sleepEpisodeDao().insert(episode("e1", id))
        repo.update(id, "Lua", birth)
        assertEquals(1, db.sleepEpisodeDao().observeByChild(id).first().size)
    }

    @Test fun updateUnknownIdReturnsFalse() = runBlocking {
        assertFalse(repo.update("inexistente", "X", birth))
    }

    @Test fun deleteRemovesProfileAndItsEpisodes() = runBlocking {
        repo.create("Luna", birth)
        val id = repo.observeProfile().first()!!.id
        db.sleepEpisodeDao().insert(episode("e1", id))
        db.sleepEpisodeDao().insert(episode("e2", id))

        assertTrue(repo.delete(id))

        assertNull(repo.observeProfile().first())
        assertEquals(0, db.sleepEpisodeDao().observeByChild(id).first().size)
    }

    @Test fun deleteUnknownIdReturnsFalseAndKeepsData() = runBlocking {
        repo.create("Luna", birth)
        assertFalse(repo.delete("inexistente"))
        assertNotNull(repo.observeProfile().first())
    }

    @Test fun canCreateAgainAfterDelete() = runBlocking {
        repo.create("Luna", birth)
        repo.delete(repo.observeProfile().first()!!.id)
        assertEquals(CreateProfileResult.CREATED, repo.create("Nova", birth))
    }
}
