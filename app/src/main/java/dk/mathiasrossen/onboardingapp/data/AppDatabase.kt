package dk.mathiasrossen.onboardingapp.data

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import dk.mathiasrossen.onboardingapp.data.article.Article
import dk.mathiasrossen.onboardingapp.data.article.ArticleDao
import dk.mathiasrossen.onboardingapp.data.article.FavoriteArticle
import dk.mathiasrossen.onboardingapp.data.article.FavoriteArticleDao
import dk.mathiasrossen.onboardingapp.data.room_type_converters.LocalDateTimeConverter

@Database(entities = [Article::class, FavoriteArticle::class], version = 1, exportSchema = false)
@TypeConverters(LocalDateTimeConverter::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun articleDao(): ArticleDao
    abstract fun favoriteArticleDao(): FavoriteArticleDao
}