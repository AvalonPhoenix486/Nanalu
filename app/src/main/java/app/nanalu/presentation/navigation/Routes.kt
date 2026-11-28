package app.nanalu.presentation.navigation

object Routes {
    const val EDIT_ARG = "edit"
    const val PROFILE_FORM_PATTERN = "profile_form?$EDIT_ARG={$EDIT_ARG}"

    fun profileForm(edit: Boolean) = "profile_form?$EDIT_ARG=$edit"
}
