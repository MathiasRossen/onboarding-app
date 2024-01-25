package dk.mathiasrossen.onboardingapp.use_cases

import dk.mathiasrossen.onboardingapp.data.article.Article
import dk.mathiasrossen.onboardingapp.data.article.ArticleRepository
import io.reactivex.rxjava3.core.Single

class ArticlesUseCase(private val articleRepository: ArticleRepository) {
    fun getArticles(sourceId: String, sortBy: String, from: String?): Single<List<Article>> =
        articleRepository.getArticlesFromSource(sourceId, sortBy, from)
}