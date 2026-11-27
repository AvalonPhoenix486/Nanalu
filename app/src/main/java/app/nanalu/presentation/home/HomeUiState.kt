package app.nanalu.presentation.home

import app.nanalu.domain.age.Age

data class ChildSummary(val name: String?, val age: Age?)

data class HomeUiState(
    val isLoading: Boolean = true,
    val loadFailed: Boolean = false,
    val child: ChildSummary? = null,
)
