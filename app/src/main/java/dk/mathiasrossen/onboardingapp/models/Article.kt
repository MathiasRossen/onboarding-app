package dk.mathiasrossen.onboardingapp.models

import org.joda.time.LocalDateTime

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
    val authorAndPublishedAt get() = "$author - ${publishedAt.toString("yyyy-MM-dd HH:mm")}"

    data class Source(val id: String?, val name: String)

    companion object {
        fun createSample(): Article = Article(
            Source("", ""),
            "Look at my horse, my horse is amazing - You should definately check out my creature",
            "John Doe",
            "This horse can do a lot of fabolous tricks. The funny thing about my horse is that if you lick it, you get the taste of raisins.",
            "",
            "",
            LocalDateTime("2024-01-16T10:05:12"),
            ""
        )
    }
}