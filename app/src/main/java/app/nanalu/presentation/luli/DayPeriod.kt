package app.nanalu.presentation.luli

import java.time.LocalTime

/**
 * Período do dia que define a aparência da Luli e do céu. Uso apenas visual e decorativo:
 * os limites são provisórios e não têm significado científico nem entram em cálculos de sono.
 */
enum class DayPeriod {
    NIGHT,
    DAWN,
    DAY,
    ;

    companion object {
        fun fromTime(time: LocalTime): DayPeriod = when (time.hour) {
            in 5..7 -> DAWN
            in 8..18 -> DAY
            else -> NIGHT
        }
    }
}
