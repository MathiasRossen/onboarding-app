package dk.mathiasrossen.onboardingapp.api.response_models

import dk.mathiasrossen.onboardingapp.models.NewsSource

data class NewsSourcesResponse(
    val status: String,
    val sources: List<NewsSource>
)