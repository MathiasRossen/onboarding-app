package dk.mathiasrossen.onboardingapp.ui.base

import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.SnackbarResult
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.res.stringResource
import dk.mathiasrossen.onboardingapp.R
import dk.mathiasrossen.onboardingapp.utils.errors.AppError
import dk.mathiasrossen.onboardingapp.utils.errors.RetryActionError

@Composable
fun SnackbarErrors(
    errors: List<AppError>,
    snackbarHostState: SnackbarHostState,
    onDismissError: (error: AppError) -> Unit,
    onAction: () -> Unit
) {
    if (errors.isNotEmpty()) {
        val displayError = errors.last()
        val message = stringResource(displayError.messageStringResource)
        val actionLabel = if (displayError is RetryActionError) {
            stringResource(R.string.base_error_retry)
        } else {
            stringResource(R.string.base_error_dismiss)
        }
        LaunchedEffect(snackbarHostState) {
            val result = snackbarHostState.showSnackbar(
                message = message,
                actionLabel = actionLabel,
                duration = SnackbarDuration.Indefinite
            )
            when (result) {
                SnackbarResult.ActionPerformed -> {
                    onAction()
                    onDismissError(displayError)
                }
                else -> onDismissError(displayError)
            }
        }
    }
}
