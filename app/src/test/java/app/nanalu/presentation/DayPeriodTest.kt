package app.nanalu.presentation

import app.nanalu.presentation.luli.DayPeriod
import java.time.LocalTime
import org.junit.Assert.assertEquals
import org.junit.Test

class DayPeriodTest {
    private fun at(h: Int, m: Int = 0) = DayPeriod.fromTime(LocalTime.of(h, m))

    @Test fun midnightAndEarlyMorningAreNight() {
        assertEquals(DayPeriod.NIGHT, at(0))
        assertEquals(DayPeriod.NIGHT, at(4, 59))
    }

    @Test fun dawnBoundaries() {
        assertEquals(DayPeriod.DAWN, at(5))
        assertEquals(DayPeriod.DAWN, at(7, 59))
    }

    @Test fun dayBoundaries() {
        assertEquals(DayPeriod.DAY, at(8))
        assertEquals(DayPeriod.DAY, at(18, 59))
    }

    @Test fun eveningIsNight() {
        assertEquals(DayPeriod.NIGHT, at(19))
        assertEquals(DayPeriod.NIGHT, at(23, 59))
    }
}
