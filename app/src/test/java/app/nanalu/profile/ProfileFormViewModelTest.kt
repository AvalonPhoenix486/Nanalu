package app.nanalu.profile

import app.nanalu.FakeChildProfileRepository
import app.nanalu.domain.profile.ProfileFieldError
import app.nanalu.presentation.profile.ProfileFormViewModel
import app.nanalu.presentation.profile.SaveError
import app.nanalu.sampleProfile
import java.io.IOException
import java.time.Clock
import java.time.Instant
import java.time.LocalDate
import java.time.ZoneOffset
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertNull
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class ProfileFormViewModelTest {
    private val clock = Clock.fixed(Instant.parse("2026-11-27T12:00:00Z"), ZoneOffset.UTC)
    private val birth = LocalDate.parse("2026-08-15")

    @Before fun setUp() = Dispatchers.setMain(UnconfinedTestDispatcher())

    @After fun tearDown() = Dispatchers.resetMain()

    private fun createVm(repo: FakeChildProfileRepository) = ProfileFormViewModel(repo, isEditing = false, clock = clock)
    private fun editVm(repo: FakeChildProfileRepository) = ProfileFormViewModel(repo, isEditing = true, clock = clock)

    @Test fun createWithoutNameSavesNullName() = runTest {
        val repo = FakeChildProfileRepository()
        val vm = createVm(repo)
        vm.onBirthDateChange(birth)
        vm.save()
        assertTrue(vm.uiState.value.saved)
        assertNull(repo.current.value!!.name)
        assertEquals(birth, repo.current.value!!.birthDate)
    }

    @Test fun createTrimsName() = runTest {
        val repo = FakeChildProfileRepository()
        val vm = createVm(repo)
        vm.onNameChange("  Luna  ")
        vm.onBirthDateChange(birth)
        vm.save()
        assertEquals("Luna", repo.current.value!!.name)
    }

    @Test fun missingBirthDateShowsErrorAndDoesNotWrite() = runTest {
        val repo = FakeChildProfileRepository()
        val vm = createVm(repo)
        vm.onNameChange("Luna")
        vm.save()
        assertEquals(ProfileFieldError.BIRTH_DATE_MISSING, vm.uiState.value.birthDateError)
        assertFalse(vm.uiState.value.saved)
        assertEquals(0, repo.createCalls)
    }

    @Test fun futureBirthDateIsRejectedWithoutWriting() = runTest {
        val repo = FakeChildProfileRepository()
        val vm = createVm(repo)
        vm.onBirthDateChange(LocalDate.parse("2026-11-28"))
        vm.save()
        assertEquals(ProfileFieldError.BIRTH_DATE_IN_FUTURE, vm.uiState.value.birthDateError)
        assertEquals(0, repo.createCalls)
    }

    @Test fun tooLongNameIsRejectedWithoutWriting() = runTest {
        val repo = FakeChildProfileRepository()
        val vm = createVm(repo)
        vm.onNameChange("a".repeat(51))
        vm.onBirthDateChange(birth)
        vm.save()
        assertEquals(ProfileFieldError.NAME_TOO_LONG, vm.uiState.value.nameError)
        assertEquals(0, repo.createCalls)
    }

    @Test fun editingAFieldClearsItsError() = runTest {
        val vm = createVm(FakeChildProfileRepository())
        vm.save()
        assertEquals(ProfileFieldError.BIRTH_DATE_MISSING, vm.uiState.value.birthDateError)
        vm.onBirthDateChange(birth)
        assertNull(vm.uiState.value.birthDateError)
    }

    @Test fun createWhenProfileExistsReportsAlreadyExists() = runTest {
        val repo = FakeChildProfileRepository(sampleProfile())
        val vm = createVm(repo)
        vm.onBirthDateChange(birth)
        vm.save()
        assertEquals(SaveError.ALREADY_EXISTS, vm.uiState.value.saveError)
        assertFalse(vm.uiState.value.saved)
    }

    @Test fun storageFailureIsExplicitAndAllowsRetry() = runTest {
        val repo = FakeChildProfileRepository().apply { writeFailure = IOException("disco") }
        val vm = createVm(repo)
        vm.onBirthDateChange(birth)
        vm.save()
        assertEquals(SaveError.FAILED, vm.uiState.value.saveError)
        assertFalse(vm.uiState.value.saved)
        assertFalse(vm.uiState.value.isSaving)

        repo.writeFailure = null
        vm.save()
        assertTrue(vm.uiState.value.saved)
    }

    @Test fun savingTwiceDoesNotCreateTwice() = runTest {
        val repo = FakeChildProfileRepository()
        val vm = createVm(repo)
        vm.onBirthDateChange(birth)
        vm.save()
        vm.save()
        assertEquals(1, repo.createCalls)
    }

    @Test fun dirtyTracking() = runTest {
        val vm = createVm(FakeChildProfileRepository())
        assertFalse(vm.uiState.value.isDirty)
        vm.onNameChange("Lu")
        assertTrue(vm.uiState.value.isDirty)
        vm.onNameChange("")
        assertFalse(vm.uiState.value.isDirty)
    }

    @Test fun editLoadsExistingValues() = runTest {
        val vm = editVm(FakeChildProfileRepository(sampleProfile("Luna", "2026-08-15")))
        val state = vm.uiState.value
        assertFalse(state.isLoading)
        assertEquals("Luna", state.name)
        assertEquals(birth, state.birthDate)
        assertFalse(state.isDirty)
    }

    @Test fun editSavesChangesAndKeepsSingleProfile() = runTest {
        val repo = FakeChildProfileRepository(sampleProfile("Luna", "2026-08-15"))
        val vm = editVm(repo)
        vm.onNameChange("Lua")
        vm.onBirthDateChange(LocalDate.parse("2026-08-10"))
        assertTrue(vm.uiState.value.isDirty)
        vm.save()
        assertTrue(vm.uiState.value.saved)
        assertEquals("Lua", repo.current.value!!.name)
        assertEquals(LocalDate.parse("2026-08-10"), repo.current.value!!.birthDate)
        assertEquals(0, repo.createCalls)
    }

    @Test fun editCanClearTheName() = runTest {
        val repo = FakeChildProfileRepository(sampleProfile("Luna"))
        val vm = editVm(repo)
        vm.onNameChange("   ")
        vm.save()
        assertNull(repo.current.value!!.name)
    }

    @Test fun editWithoutProfileIsReportedAndCannotSave() = runTest {
        val repo = FakeChildProfileRepository()
        val vm = editVm(repo)
        assertTrue(vm.uiState.value.profileMissing)
        vm.save()
        assertEquals(0, repo.updateCalls)
    }

    @Test fun editLoadFailureIsReported() = runTest {
        val repo = FakeChildProfileRepository().apply { observeFailure = IOException("x") }
        val vm = editVm(repo)
        assertTrue(vm.uiState.value.loadFailed)
        assertFalse(vm.uiState.value.profileMissing)
    }

    @Test fun editWhenProfileWasDeletedMeanwhileReportsNotFound() = runTest {
        val repo = FakeChildProfileRepository(sampleProfile())
        val vm = editVm(repo)
        repo.current.value = null
        vm.onNameChange("Outro")
        vm.save()
        assertEquals(SaveError.NOT_FOUND, vm.uiState.value.saveError)
        assertFalse(vm.uiState.value.saved)
    }
}
