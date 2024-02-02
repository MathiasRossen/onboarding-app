package dk.mathiasrossen.onboardingapp.data.article

import dk.mathiasrossen.onboardingapp.api.NewsApiService
import dk.mathiasrossen.onboardingapp.utils.mappers.ArticleMapper
import io.reactivex.rxjava3.core.Single

class ArticleRepository(
    private val newsApiService: NewsApiService,
    private val articleDao: ArticleDao
) {
    fun getArticlesFromSource(sourceId: String, sortBy: String, from: String?): Single<List<Article>> =
        newsApiService.getArticlesFromSource(
            source = sourceId,
            sortBy = sortBy,
            from = from
        ).map { articlesResponse ->
            val articleMapper = ArticleMapper()
            val articles = articlesResponse.articles.map { article ->
                articleMapper.toArticle(article)
            }
            storeArticles(articles)
            articles
        }

    private fun storeArticles(articles: List<Article>) = articleDao.insertAll(articles)

    fun findArticle(uuid: String): Single<Article> = articleDao.findByUuid(uuid)

    fun getArticlesByUrls(urls: List<String>) = articleDao.allByUrls(urls)
}