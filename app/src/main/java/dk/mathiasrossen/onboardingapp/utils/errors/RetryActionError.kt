package dk.mathiasrossen.onboardingapp.utils.errors

import androidx.annotation.StringRes

data class RetryActionError(@StringRes override val messageStringResource: Int) : AppError
