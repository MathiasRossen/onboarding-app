package dk.mathiasrossen.onboardingapp.api.response_models

import java.time.LocalDateTime

data class ArticlesResponse(
    val status: String,
    val totalResults: Int,
    val articles: List<Article>
) {
    data class Article(
        val source: Source,
        val title: String,
        val author: String,
        val description: String?,
        val url: String,
        val urlToImage: String?,
        val publishedAt: LocalDateTime,
        val content: String
    ) {
        data class Source(val id: String, val name: String)
    }
}