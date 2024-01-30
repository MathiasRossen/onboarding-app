package dk.mathiasrossen.onboardingapp.data.article

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class FavoriteArticle(
    @PrimaryKey
    val articleUrl: String
)