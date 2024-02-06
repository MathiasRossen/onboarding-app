package dk.mathiasrossen.onboardingapp.ui.sources

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.SnackbarResult
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import dk.mathiasrossen.onboardingapp.R
import dk.mathiasrossen.onboardingapp.api.response_models.NewsSourcesResponse
import dk.mathiasrossen.onboardingapp.ui.base.ErrorScreen
import dk.mathiasrossen.onboardingapp.ui.base.LoadingScreen

@Composable
fun SourcesScreen(
    sourcesViewModel: SourcesViewModel = hiltViewModel(),
    snackbarHostState: SnackbarHostState,
    onNewsSourceClick: (sourceId: String, sourceName: String) -> Unit
) {
    val hasError by sourcesViewModel.hasError
    val isLoading by sourcesViewModel.isLoading
    if (isLoading) {
        LoadingScreen()
    } else if (hasError) {
        val snackbarMessage = stringResource(R.string.sources_error)
        val snackbarActionLabel = stringResource(R.string.base_error_retry)
        LaunchedEffect(snackbarHostState) {
            val result = snackbarHostState.showSnackbar(
                message = snackbarMessage,
                actionLabel = snackbarActionLabel,
                duration = SnackbarDuration.Indefinite
            )
            if (result == SnackbarResult.ActionPerformed) {
                sourcesViewModel.loadSources()
            }
        }
        ErrorScreen()
    } else {
        SourcesList(newsSources = sourcesViewModel.newsSources.value, onNewsSourceClick = onNewsSourceClick)
    }

}

@Composable
private fun SourcesList(
    newsSources: List<NewsSourcesResponse.NewsSource>,
    onNewsSourceClick: (sourceId: String, sourceName: String) -> Unit
) {
    LazyColumn(
        contentPadding = PaddingValues(dimensionResource(id = R.dimen.base_content_padding)),
        verticalArrangement = Arrangement.spacedBy(dimensionResource(id = R.dimen.base_arrangement_space_large))
    ) {
        items(newsSources) { newsSource ->
            SourceItem(newsSource, onNewsSourceClick)
        }
    }
}
