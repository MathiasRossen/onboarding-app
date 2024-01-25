package dk.mathiasrossen.onboardingapp.utils.mappers

import dk.mathiasrossen.onboardingapp.api.response_models.ArticlesResponse
import dk.mathiasrossen.onboardingapp.data.article.Article

class ArticleMapper {
    fun toArticle(articlesResponseArticle: ArticlesResponse.Article) = Article(
        articlesResponseArticle.source.id,
        articlesResponseArticle.title,
        articlesResponseArticle.author,
        articlesResponseArticle.description,
        articlesResponseArticle.url,
        articlesResponseArticle.urlToImage,
        articlesResponseArticle.publishedAt,
        articlesResponseArticle.content
    )
}