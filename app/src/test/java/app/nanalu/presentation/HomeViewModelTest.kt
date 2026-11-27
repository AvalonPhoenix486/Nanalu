package app.nanalu.presentation

import app.nanalu.domain.age.Age
import app.nanalu.domain.model.ChildProfile
import app.nanalu.domain.repository.ChildProfileRepository
import app.nanalu.presentation.home.HomeViewModel
import java.io.IOException
import java.time.Clock
import java.time.Instant
import java.time.LocalDate
import java.time.ZoneOffset
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.launch
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
class HomeViewModelTest {
    private val clock = Clock.fixed(Instant.parse("2026-11-27T12:00:00Z"), ZoneOffset.UTC)

    @Before fun setUp() = Dispatchers.setMain(UnconfinedTestDispatcher())

    @After fun tearDown() = Dispatchers.resetMain()

    private class FakeRepository(private val source: Flow<ChildProfile?>) : ChildProfileRepository {
        override fun observeProfile() = source
    }

    private fun profile(name: String?, birth: String) = ChildProfile(
        id = "1", name = name, birthDate = LocalDate.parse(birth),
        createdAt = Instant.EPOCH, updatedAt = Instant.EPOCH,
    )

    private fun kotlinx.coroutines.test.TestScope.collect(vm: HomeViewModel) =
        backgroundScope.launch(UnconfinedTestDispatcher(testScheduler)) { vm.uiState.collect {} }

    @Test fun startsLoadingBeforeAnyData() = runTest {
        val vm = HomeViewModel(FakeRepository(flowOf(null)), clock)
        assertTrue(vm.uiState.value.isLoading)
    }

    @Test fun noProfileIsEmptyNotError() = runTest {
        val vm = HomeViewModel(FakeRepository(flowOf(null)), clock)
        collect(vm)
        val state = vm.uiState.value
        assertFalse(state.isLoading)
        assertFalse(state.loadFailed)
        assertNull(state.child)
    }

    @Test fun profileExposesNameAndCalculatedAge() = runTest {
        val vm = HomeViewModel(FakeRepository(flowOf(profile("Luna", "2026-08-15"))), clock)
        collect(vm)
        val child = vm.uiState.value.child!!
        assertEquals("Luna", child.name)
        assertEquals(Age(0, 3, 12), child.age)
    }

    @Test fun nameIsOptional() = runTest {
        val vm = HomeViewModel(FakeRepository(flowOf(profile(null, "2026-08-15"))), clock)
        collect(vm)
        assertNull(vm.uiState.value.child!!.name)
    }

    @Test fun futureBirthDateYieldsNoAge() = runTest {
        val vm = HomeViewModel(FakeRepository(flowOf(profile("Luna", "2027-01-01"))), clock)
        collect(vm)
        assertNull(vm.uiState.value.child!!.age)
    }

    @Test fun readFailureIsExplicitError() = runTest {
        val vm = HomeViewModel(FakeRepository(flow { throw IOException("falha") }), clock)
        collect(vm)
        val state = vm.uiState.value
        assertFalse(state.isLoading)
        assertTrue(state.loadFailed)
        assertNull(state.child)
    }
}
