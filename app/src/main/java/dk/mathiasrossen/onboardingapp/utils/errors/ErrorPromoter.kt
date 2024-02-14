package dk.mathiasrossen.onboardingapp.utils.errors

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ErrorPromoter @Inject constructor() {
    val errors: MutableState<List<AppError>> = mutableStateOf(listOf())

    @Inject
    lateinit var errorActionBus: ErrorActionBus

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
