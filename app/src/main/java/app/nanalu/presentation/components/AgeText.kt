package app.nanalu.presentation.components

import androidx.compose.runtime.Composable
import androidx.compose.ui.res.pluralStringResource
import androidx.compose.ui.res.stringResource
import app.nanalu.R
import app.nanalu.domain.age.Age

/** "3 meses e 12 dias": só mostra as partes diferentes de zero. */
@Composable
fun ageText(age: Age): String {
    if (age.isBornToday) return stringResource(R.string.age_born_today)
    val parts = buildList {
        if (age.years > 0) add(pluralStringResource(R.plurals.age_years, age.years, age.years))
        if (age.months > 0) add(pluralStringResource(R.plurals.age_months, age.months, age.months))
        if (age.days > 0) add(pluralStringResource(R.plurals.age_days, age.days, age.days))
    }
    return if (parts.size == 1) {
        parts.first()
    } else {
        parts.dropLast(1).joinToString(", ") + " " + stringResource(R.string.age_and) + " " + parts.last()
    }
}
