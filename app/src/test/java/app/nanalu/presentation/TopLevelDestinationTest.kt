package app.nanalu.presentation

import app.nanalu.presentation.navigation.TopLevelDestination
import org.junit.Assert.assertEquals
import org.junit.Test

class TopLevelDestinationTest {
    @Test fun hasTheFiveAreasFromScreenMapInOrder() {
        assertEquals(
            listOf("home", "history", "stats", "content", "profile"),
            TopLevelDestination.entries.map { it.route },
        )
    }

    @Test fun routesAreUnique() {
        val routes = TopLevelDestination.entries.map { it.route }
        assertEquals(routes.size, routes.toSet().size)
    }
}
