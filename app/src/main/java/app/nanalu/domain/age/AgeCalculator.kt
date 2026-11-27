package app.nanalu.domain.age

import java.time.LocalDate
import java.time.Period

/** Idade em anos, meses e dias completos (RB-013). */
data class Age(val years: Int, val months: Int, val days: Int) {
    val totalMonths: Int get() = years * 12 + months
    val isBornToday: Boolean get() = years == 0 && months == 0 && days == 0
}

object AgeCalculator {
    /**
     * Usa o calendário real via [Period] (sem aproximar meses para 30 dias).
     * Retorna null quando a data de nascimento é posterior à data de referência.
     */
    fun calculate(birthDate: LocalDate, referenceDate: LocalDate): Age? {
        if (birthDate.isAfter(referenceDate)) return null
        val period = Period.between(birthDate, referenceDate)
        return Age(period.years, period.months, period.days)
    }
}
