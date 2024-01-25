package dk.mathiasrossen.onboardingapp.data.article

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import io.reactivex.rxjava3.core.Single

@Dao
interface ArticleDao {
    @Query("SELECT * FROM article WHERE sourceId = :sourceId")
    fun getFromSource(sourceId: String): Single<List<Article>>

    @Query("SELECT * FROM article WHERE uuid = :uuid LIMIT 1")
    fun findByUuid(uuid: String): Single<Article>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertAll(articles: List<Article>)
}