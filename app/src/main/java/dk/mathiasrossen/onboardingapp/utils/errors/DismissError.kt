package dk.mathiasrossen.onboardingapp.utils.errors

import androidx.annotation.StringRes
import dk.mathiasrossen.onboardingapp.R

data class DismissError(@StringRes override val messageStringResource: Int = R.string.base_error_message) : AppError
