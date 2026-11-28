package app.nanalu.domain.profile

import java.time.LocalDate

enum class ProfileFieldError { NAME_TOO_LONG, NAME_INVALID_CHARACTERS, BIRTH_DATE_MISSING, BIRTH_DATE_IN_FUTURE }

data class ProfileValidation(val nameError: ProfileFieldError?, val birthDateError: ProfileFieldError?) {
    val isValid: Boolean get() = nameError == null && birthDateError == null
}

/** Regras de FR-001 / DATA_MODEL §4: nome opcional com tamanho limitado; nascimento obrigatório e não futuro. */
object ProfileValidator {
    /** Limite de tamanho do nome ou apelido (decisão de implementação, ajustável). */
    const val MAX_NAME_LENGTH = 50

    /** Nome em branco vira ausência de nome (null). */
    fun normalizeName(raw: String): String? = raw.trim().ifEmpty { null }

    fun validate(rawName: String, birthDate: LocalDate?, today: LocalDate): ProfileValidation {
        val name = normalizeName(rawName)
        val nameError = when {
            name == null -> null
            name.any { it.isISOControl() } -> ProfileFieldError.NAME_INVALID_CHARACTERS
            name.length > MAX_NAME_LENGTH -> ProfileFieldError.NAME_TOO_LONG
            else -> null
        }
        val birthError = when {
            birthDate == null -> ProfileFieldError.BIRTH_DATE_MISSING
            birthDate.isAfter(today) -> ProfileFieldError.BIRTH_DATE_IN_FUTURE
            else -> null
        }
        return ProfileValidation(nameError, birthError)
    }
}
