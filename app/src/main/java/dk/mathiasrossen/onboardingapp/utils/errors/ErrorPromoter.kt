package dk.mathiasrossen.onboardingapp.utils.errors

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf

class ErrorPromoter {
    val errors: MutableState<List<AppError>> = mutableStateOf(listOf())

    fun submitError(error: AppError) {
        errors.value = errors.value.toMutableList().apply {
            add(error)
        }
    }

    fun submitGenericError() {
        submitError(DismissError())
    }

    fun dismissError(error: AppError) {
        errors.value = errors.value.toMutableList().apply {
            remove(error)
        }
    }

    fun dismissAllErrors() {
        errors.value = listOf()
    }
}
