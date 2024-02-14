package dk.mathiasrossen.onboardingapp.ui.sources

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.tooling.preview.Preview
import dk.mathiasrossen.onboardingapp.R
import dk.mathiasrossen.onboardingapp.api.response_models.NewsSourcesResponse
import dk.mathiasrossen.onboardingapp.ui.theme.OnboardingAppTheme
import dk.mathiasrossen.onboardingapp.ui.theme.Typography

@Composable
fun SourceItem(
    newsSource: NewsSourcesResponse.NewsSource,
    onNewsSourceClick: (sourceId: String, sourceName: String) -> Unit
) {
    Column(
        modifier = Modifier.clickable { onNewsSourceClick(newsSource.id, newsSource.name) },
        verticalArrangement = Arrangement.spacedBy(dimensionResource(id = R.dimen.base_arrangement_space_small))
    ) {
        Text(text = newsSource.name, style = Typography.titleLarge)
        Text(text = newsSource.description, style = Typography.bodyMedium)
    }
}

@Preview(showBackground = true)
@Composable
private fun SourceItemPreview() {
    OnboardingAppTheme {
        SourceItem(
            NewsSourcesResponse.NewsSource(
                "1",
                "HorseNews",
                "Your trusted source for breaking news, analysis, exclusive interviews, headlines, and videos related to horses at Horsenews.com",
                "https://horsenews.com/1",
                "horses",
                "en",
                "us"
            )
        ) { _, _ -> }
    }
}
