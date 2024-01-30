package dk.mathiasrossen.onboardingapp.data.article

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import io.reactivex.rxjava3.core.Single

@Dao
interface FavoriteArticleDao {
    @Query("SELECT * FROM favoritearticle WHERE articleUrl = :articleUrl LIMIT 1")
    fun find(articleUrl: String): FavoriteArticle?

    @Query("SELECT * FROM favoritearticle")
    fun getAll(): Single<List<FavoriteArticle>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insert(favoriteArticle: FavoriteArticle)

    @Delete
    fun delete(favoriteArticle: FavoriteArticle)

}