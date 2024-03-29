package dk.mathiasrossen.onboardingapp.ui.articles.details

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import dagger.hilt.android.lifecycle.HiltViewModel
import dk.mathiasrossen.onboardingapp.data.article.Article
import dk.mathiasrossen.onboardingapp.dependency_injection.annotations.IoScheduler
import dk.mathiasrossen.onboardingapp.dependency_injection.annotations.UiScheduler
import dk.mathiasrossen.onboardingapp.use_cases.ArticlesUseCase
import dk.mathiasrossen.onboardingapp.utils.BaseViewModel
import io.reactivex.rxjava3.core.Scheduler
import javax.inject.Inject

@HiltViewModel
class ArticleDetailsViewModel @Inject constructor(
    private val articlesUseCase: ArticlesUseCase,
    @IoScheduler
    private val ioScheduler: Scheduler,
    @UiScheduler
    private val uiScheduler: Scheduler,
    savedStateHandle: SavedStateHandle
) : BaseViewModel() {
    private val articleUuid: String = savedStateHandle[ARTICLE_UUID_KEY] ?: ""

    val articleState = mutableStateOf<Article?>(null)
    val favoriteState = mutableStateOf(false)

    init {
        compositeDisposable.add(
            articlesUseCase.findArticle(articleUuid)
                .subscribeOn(ioScheduler)
                .flatMap(
                    { article -> articlesUseCase.getIsArticleFavorite(article) },
                    { article, isFavorite -> article to isFavorite })
                .observeOn(uiScheduler)
                .subscribe({ articleAndFavoritePair ->
                    articleState.value = articleAndFavoritePair.first
                    favoriteState.value = articleAndFavoritePair.second
                }) { errorPromoter.submitGenericError() }
        )
    }

    fun toggleFavorite() {
        articleState.value?.let { article ->
            compositeDisposable.add(
                articlesUseCase.toggleFavorite(article)
                    .subscribeOn(ioScheduler)
                    .subscribe({ isFavorite ->
                        favoriteState.value = isFavorite
                    }) { errorPromoter.submitGenericError() }
            )
        }
    }

    companion object {
        const val ARTICLE_UUID_KEY = "articleUuid"
    }
}
