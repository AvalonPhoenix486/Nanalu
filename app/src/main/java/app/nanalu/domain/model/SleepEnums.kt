package app.nanalu.domain.model

/** DATA_MODEL §5. DELETED não existe no MVP: a exclusão é efetiva. */
enum class SleepEpisodeStatus { ACTIVE, COMPLETED, INVALID }

/** Organização do registro, não determinação fisiológica. */
enum class SleepClassification { DAYTIME, NIGHTTIME, UNCLASSIFIED }

/** No MVP só existe registro manual. */
enum class SleepSource { MANUAL }
