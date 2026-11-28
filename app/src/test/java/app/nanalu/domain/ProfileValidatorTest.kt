package app.nanalu.domain

import app.nanalu.domain.profile.ProfileFieldError
import app.nanalu.domain.profile.ProfileValidator
import java.time.LocalDate
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNull
import org.junit.Assert.assertTrue
import org.junit.Test

class ProfileValidatorTest {
    private val today = LocalDate.parse("2026-11-27")
    private val validBirth = LocalDate.parse("2026-08-15")

    @Test fun nameIsOptional() {
        val result = ProfileValidator.validate("", validBirth, today)
        assertTrue(result.isValid)
    }

    @Test fun blankNameNormalizesToNull() {
        assertNull(ProfileValidator.normalizeName("   "))
    }

    @Test fun nameIsTrimmed() {
        assertEquals("Luna", ProfileValidator.normalizeName("  Luna "))
    }

    @Test fun nameAtLimitIsAcceptedAndAboveIsRejected() {
        val atLimit = "a".repeat(ProfileValidator.MAX_NAME_LENGTH)
        assertTrue(ProfileValidator.validate(atLimit, validBirth, today).isValid)
        assertEquals(
            ProfileFieldError.NAME_TOO_LONG,
            ProfileValidator.validate(atLimit + "a", validBirth, today).nameError,
        )
    }

    @Test fun surroundingSpacesDoNotCountTowardLimit() {
        val padded = "  " + "a".repeat(ProfileValidator.MAX_NAME_LENGTH) + "  "
        assertTrue(ProfileValidator.validate(padded, validBirth, today).isValid)
    }

    @Test fun controlCharactersAreRejected() {
        assertEquals(
            ProfileFieldError.NAME_INVALID_CHARACTERS,
            ProfileValidator.validate("Lu\nna", validBirth, today).nameError,
        )
    }

    @Test fun birthDateIsRequired() {
        assertEquals(ProfileFieldError.BIRTH_DATE_MISSING, ProfileValidator.validate("Luna", null, today).birthDateError)
    }

    @Test fun futureBirthDateIsRejected() {
        assertEquals(
            ProfileFieldError.BIRTH_DATE_IN_FUTURE,
            ProfileValidator.validate("", today.plusDays(1), today).birthDateError,
        )
    }

    @Test fun birthDateTodayIsAccepted() {
        assertTrue(ProfileValidator.validate("", today, today).isValid)
    }

    @Test fun errorsAreReportedTogether() {
        val result = ProfileValidator.validate("a".repeat(60), null, today)
        assertEquals(ProfileFieldError.NAME_TOO_LONG, result.nameError)
        assertEquals(ProfileFieldError.BIRTH_DATE_MISSING, result.birthDateError)
    }
}
