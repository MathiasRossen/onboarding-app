package dk.mathiasrossen.onboardingapp.ui.sources

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.dimensionResource
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.compose.LifecycleEventEffect
import dk.mathiasrossen.onboardingapp.R
import dk.mathiasrossen.onboardingapp.api.response_models.NewsSourcesResponse
import dk.mathiasrossen.onboardingapp.ui.base.ErrorScreen
import dk.mathiasrossen.onboardingapp.ui.base.LoadingScreen

@Composable
fun SourcesScreen(
    sourcesViewModel: SourcesViewModel = hiltViewModel(),
    onNewsSourceClick: (sourceId: String, sourceName: String) -> Unit
) {
    LifecycleEventEffect(Lifecycle.Event.ON_RESUME) {
        sourcesViewModel.onResume()
    }
    val sources = sourcesViewModel.newsSources.value
    if (sourcesViewModel.isLoading.value) {
        LoadingScreen()
    } else if (sources.isEmpty()) {
        ErrorScreen()
    } else {
        SourcesList(newsSources = sources, onNewsSourceClick = onNewsSourceClick)
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
