package dk.mathiasrossen.onboardingapp.use_cases

import dk.mathiasrossen.onboardingapp.data.article.Article
import dk.mathiasrossen.onboardingapp.data.article.ArticleRepository
import dk.mathiasrossen.onboardingapp.data.article.FavoriteArticle
import dk.mathiasrossen.onboardingapp.data.article.FavoriteArticleDao
import io.reactivex.rxjava3.core.Single

class ArticlesUseCase(
    private val articleRepository: ArticleRepository,
    private val favoriteArticleDao: FavoriteArticleDao
) {
    fun getArticles(sourceId: String, sortBy: String, from: String?): Single<List<Article>> =
        articleRepository.getArticlesFromSource(sourceId, sortBy, from)

    fun findArticle(uuid: String): Single<Article> = articleRepository.findArticle(uuid)

    fun getFavoriteArticles(): Single<List<FavoriteArticle>> =
        favoriteArticleDao.getAll()

    fun getIsArticleFavorited(article: Article): Single<Boolean> =
        Single.fromCallable { favoriteArticleDao.find(article.url) !== null }

    fun toggleFavorite(article: Article): Single<Boolean> =
        Single.fromCallable {
            val favoriteArticle = favoriteArticleDao.find(article.url)
            if (favoriteArticle == null) {
                favoriteArticleDao.insert(FavoriteArticle(articleUrl = article.url))
                true
            } else {
                favoriteArticleDao.delete(favoriteArticle)
                false
            }
        }
}