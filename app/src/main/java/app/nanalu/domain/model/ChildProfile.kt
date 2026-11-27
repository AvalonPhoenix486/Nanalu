package app.nanalu.domain.model

import java.time.Instant
import java.time.LocalDate

/** Criança acompanhada. O nome é opcional (FR-001); a data de nascimento é obrigatória. */
data class ChildProfile(
    val id: String,
    val name: String?,
    val birthDate: LocalDate,
    val createdAt: Instant,
    val updatedAt: Instant,
)
