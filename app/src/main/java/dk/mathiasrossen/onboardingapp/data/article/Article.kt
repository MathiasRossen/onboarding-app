package dk.mathiasrossen.onboardingapp.data.article

import androidx.room.Entity
import androidx.room.PrimaryKey
import dk.mathiasrossen.onboardingapp.utils.date.DateUtils
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.UUID

@Entity
data class Article(
    val title: String,
    val author: String,
    val description: String,
    @PrimaryKey
    val url: String,
    val urlToImage: String,
    val publishedAt: LocalDateTime,
    val content: String,
    val uuid: String = UUID.randomUUID().toString(),
) {
    val authorAndPublishedAt: String
        get() {
            val formattedDate = publishedAt.format(DateTimeFormatter.ofPattern(DateUtils.DEFAULT_DISPLAY_PATTERN))
            return "$author - $formattedDate"
        }

    companion object {
        fun createSample(url: String = ""): Article = Article(
            "Look at my horse, my horse is amazing - You should definately check out my creature",
            "John Doe",
            "This horse can do a lot of fabolous tricks. The funny thing about my horse is that if you lick it, you get the taste of raisins.",
            url,
            "",
            LocalDateTime.parse("2024-01-16T10:05:12"),
            ""
        )
    }
}