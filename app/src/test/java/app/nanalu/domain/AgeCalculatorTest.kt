package app.nanalu.domain

import app.nanalu.domain.age.Age
import app.nanalu.domain.age.AgeCalculator
import java.time.LocalDate
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertNull
import org.junit.Assert.assertTrue
import org.junit.Test

class AgeCalculatorTest {
    private fun age(birth: String, ref: String) = AgeCalculator.calculate(LocalDate.parse(birth), LocalDate.parse(ref))

    @Test fun bornToday() {
        val result = age("2026-11-27", "2026-11-27")
        assertEquals(Age(0, 0, 0), result)
        assertTrue(result!!.isBornToday)
    }

    @Test fun monthsAndDays() {
        assertEquals(Age(0, 3, 12), age("2026-08-15", "2026-11-27"))
    }

    @Test fun completeYears() {
        assertEquals(Age(3, 0, 0), age("2023-11-27", "2026-11-27"))
    }

    @Test fun crossesYearBoundary() {
        assertEquals(Age(0, 1, 0), age("2025-12-31", "2026-01-31"))
    }

    @Test fun monthsAreNotApproximatedAs30Days() {
        // 31 dias entre 1º/jan e 1º/fev ainda são exatamente 1 mês.
        assertEquals(Age(0, 1, 0), age("2026-01-01", "2026-02-01"))
        assertEquals(Age(0, 0, 30), age("2026-01-01", "2026-01-31"))
    }

    @Test fun leapDayBirth() {
        assertEquals(Age(0, 1, 0), age("2024-02-29", "2024-03-29"))
    }

    @Test fun totalMonths() {
        assertEquals(14, Age(1, 2, 5).totalMonths)
        assertFalse(Age(1, 2, 5).isBornToday)
    }

    @Test fun futureBirthDateIsRejected() {
        assertNull(age("2026-11-28", "2026-11-27"))
    }
}
