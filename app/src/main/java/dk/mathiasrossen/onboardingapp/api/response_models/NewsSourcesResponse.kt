package dk.mathiasrossen.onboardingapp.api.response_models

data class NewsSourcesResponse(
    val status: String,
    val sources: List<NewsSource>
) {
    val sourcesSorted: List<NewsSource> get() = sources.sortedBy { newsSource -> newsSource.name }

    data class NewsSource(
        val id: String,
        val name: String,
        val description: String,
        val url: String,
        val category: String,
        val language: String,
        val country: String
    )
}