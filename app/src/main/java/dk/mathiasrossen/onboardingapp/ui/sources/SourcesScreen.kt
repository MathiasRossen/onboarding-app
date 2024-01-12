package dk.mathiasrossen.onboardingapp.ui.sources

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import dk.mathiasrossen.onboardingapp.models.NewsSource
import dk.mathiasrossen.onboardingapp.ui.theme.OnboardingAppTheme
import dk.mathiasrossen.onboardingapp.ui.theme.Typography

@Composable
fun SourcesScreen(sourcesScreenViewModel: SourcesScreenViewModel = viewModel()) {
    sourcesScreenViewModel.fetchSources()
    val sourcesState by sourcesScreenViewModel.newsSources.collectAsState()
    LazyColumn(
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(26.dp)
    ) {
        items(sourcesState) { newsSource ->
            NewsSourceView(newsSource)
        }
    }
}

@Composable
fun NewsSourceView(newsSource: NewsSource) {
    Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
        Text(text = newsSource.name, style = Typography.titleLarge)
        Text(text = newsSource.description, style = Typography.bodyMedium)
    }
}

@Preview(showBackground = true)
@Composable
fun NewsSourceViewPreview() {
    OnboardingAppTheme {
        NewsSourceView(
            NewsSource(
                "1",
                "HorseNews",
                "Your trusted source for breaking news, analysis, exclusive interviews, headlines, and videos related to horses at Horsenews.com",
                "https://horsenews.com/1",
                "horses",
                "en",
                "us"
            )
        )
    }
}