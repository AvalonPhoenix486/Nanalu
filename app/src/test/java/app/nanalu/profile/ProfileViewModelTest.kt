package app.nanalu.profile

import app.nanalu.FakeChildProfileRepository
import app.nanalu.domain.age.Age
import app.nanalu.presentation.profile.ProfileEvent
import app.nanalu.presentation.profile.ProfileViewModel
import app.nanalu.sampleProfile
import java.io.IOException
import java.time.Clock
import java.time.Instant
import java.time.ZoneOffset
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.TestScope
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertNotNull
import org.junit.Assert.assertNull
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class ProfileViewModelTest {
    private val clock = Clock.fixed(Instant.parse("2026-11-27T12:00:00Z"), ZoneOffset.UTC)

    @Before fun setUp() = Dispatchers.setMain(UnconfinedTestDispatcher())

    @After fun tearDown() = Dispatchers.resetMain()

    private fun TestScope.collect(vm: ProfileViewModel) =
        backgroundScope.launch(UnconfinedTestDispatcher(testScheduler)) { vm.uiState.collect {} }

    @Test fun startsLoading() = runTest {
        val vm = ProfileViewModel(FakeChildProfileRepository(), clock)
        assertTrue(vm.uiState.value.isLoading)
    }

    @Test fun noProfileIsEmptyState() = runTest {
        val vm = ProfileViewModel(FakeChildProfileRepository(), clock)
        collect(vm)
        val state = vm.uiState.value
        assertFalse(state.isLoading)
        assertFalse(state.loadFailed)
        assertNull(state.profile)
    }

    @Test fun profileIsExposedWithAge() = runTest {
        val vm = ProfileViewModel(FakeChildProfileRepository(sampleProfile("Luna", "2026-08-15")), clock)
        collect(vm)
        val profile = vm.uiState.value.profile!!
        assertEquals("Luna", profile.name)
        assertEquals(Age(0, 3, 12), profile.age)
    }

    @Test fun readFailureIsExplicit() = runTest {
        val repo = FakeChildProfileRepository().apply { observeFailure = IOException("x") }
        val vm = ProfileViewModel(repo, clock)
        collect(vm)
        assertTrue(vm.uiState.value.loadFailed)
        assertNull(vm.uiState.value.profile)
    }

    @Test fun deleteRequiresConfirmation() = runTest {
        val repo = FakeChildProfileRepository(sampleProfile())
        val vm = ProfileViewModel(repo, clock)
        collect(vm)
        vm.requestDelete()
        assertTrue(vm.uiState.value.showDeleteConfirmation)
        assertEquals(0, repo.deleteCalls)
        assertNotNull(repo.current.value)
    }

    @Test fun cancellingDeleteKeepsProfile() = runTest {
        val repo = FakeChildProfileRepository(sampleProfile())
        val vm = ProfileViewModel(repo, clock)
        collect(vm)
        vm.requestDelete()
        vm.dismissDelete()
        assertFalse(vm.uiState.value.showDeleteConfirmation)
        assertEquals(0, repo.deleteCalls)
        assertNotNull(vm.uiState.value.profile)
    }

    @Test fun confirmedDeleteRemovesProfileAndEmitsEvent() = runTest {
        val repo = FakeChildProfileRepository(sampleProfile())
        val vm = ProfileViewModel(repo, clock)
        collect(vm)
        vm.requestDelete()
        vm.confirmDelete()
        assertEquals(1, repo.deleteCalls)
        assertNull(repo.current.value)
        assertNull(vm.uiState.value.profile)
        assertFalse(vm.uiState.value.showDeleteConfirmation)
        assertFalse(vm.uiState.value.isDeleting)
        assertEquals(ProfileEvent.DELETED, vm.events.first())
    }

    @Test fun deleteFailureKeepsProfileAndEmitsFailureEvent() = runTest {
        val repo = FakeChildProfileRepository(sampleProfile()).apply { writeFailure = IOException("x") }
        val vm = ProfileViewModel(repo, clock)
        collect(vm)
        vm.requestDelete()
        vm.confirmDelete()
        assertNotNull(vm.uiState.value.profile)
        assertFalse(vm.uiState.value.isDeleting)
        assertEquals(ProfileEvent.DELETE_FAILED, vm.events.first())
    }

    @Test fun deleteWithoutProfileDoesNothing() = runTest {
        val repo = FakeChildProfileRepository()
        val vm = ProfileViewModel(repo, clock)
        collect(vm)
        vm.requestDelete()
        vm.confirmDelete()
        assertFalse(vm.uiState.value.showDeleteConfirmation)
        assertEquals(0, repo.deleteCalls)
    }
}
