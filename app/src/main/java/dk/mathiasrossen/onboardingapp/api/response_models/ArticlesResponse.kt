package dk.mathiasrossen.onboardingapp.api.response_models

import dk.mathiasrossen.onboardingapp.models.Article

data class ArticlesResponse(
    val status: String,
    val totalResults: Int,
    val articles: List<Article>
)