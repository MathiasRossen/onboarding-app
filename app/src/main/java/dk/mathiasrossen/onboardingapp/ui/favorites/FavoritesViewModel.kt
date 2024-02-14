package dk.mathiasrossen.onboardingapp.ui.favorites

import androidx.compose.runtime.mutableStateOf
import dagger.hilt.android.lifecycle.HiltViewModel
import dk.mathiasrossen.onboardingapp.data.article.Article
import dk.mathiasrossen.onboardingapp.dependency_injection.annotations.IoScheduler
import dk.mathiasrossen.onboardingapp.use_cases.ArticlesUseCase
import dk.mathiasrossen.onboardingapp.utils.BaseViewModel
import io.reactivex.rxjava3.core.Scheduler
import javax.inject.Inject

@HiltViewModel
class FavoritesViewModel @Inject constructor(
    private val articlesUseCase: ArticlesUseCase,
    @IoScheduler private val ioScheduler: Scheduler
) : BaseViewModel() {
    val articles = mutableStateOf<List<Article>>(listOf())

    private fun loadArticles() {
        compositeDisposable.add(
            articlesUseCase.getFavoriteArticles()
                .subscribeOn(ioScheduler)
                .flatMap { favoriteArticles ->
                    val urls = favoriteArticles.map { it.articleUrl }
                    articlesUseCase.getArticlesByUrls(urls)
                }
                .subscribe({ articleList ->
                    articles.value = articleList
                }) { errorPromoter.submitGenericError() }
        )
    }

    fun toggleFavorite(article: Article) {
        compositeDisposable.add(
            articlesUseCase.toggleFavorite(article)
                .subscribeOn(ioScheduler)
                .subscribe({ _ ->
                    articles.value = articles.value.toMutableList().apply {
                        remove(article)
                    }
                }) { errorPromoter.submitGenericError() }
        )
    }

    fun onResume() {
        loadArticles()
    }
}
