package dk.mathiasrossen.onboardingapp.models

data class Article(
    val source: Source,
    val title: String,
    val author: String,
    val description: String,
    val url: String,
    val urlToImage: String,
    val publishedAt: String,
    val content: String
) {
    val authorAndPublishedAt get() = "$author - $publishedAt"

    data class Source(val id: String?, val name: String)
}