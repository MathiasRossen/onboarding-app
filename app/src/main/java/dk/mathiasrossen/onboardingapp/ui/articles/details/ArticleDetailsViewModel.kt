package dk.mathiasrossen.onboardingapp.ui.articles.details

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import dk.mathiasrossen.onboardingapp.data.article.Article
import dk.mathiasrossen.onboardingapp.use_cases.ArticlesUseCase
import io.reactivex.rxjava3.core.Scheduler
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.schedulers.Schedulers
import javax.inject.Inject

@HiltViewModel
class ArticleDetailsViewModel @Inject constructor(
    private val articlesUseCase: ArticlesUseCase,
    uiScheduler: Scheduler,
    savedStateHandle: SavedStateHandle
) : ViewModel() {
    private val articleUuid: String = checkNotNull(savedStateHandle[ARTICLE_UUID_KEY])
    private val compositeDisposable = CompositeDisposable()

    val articleState = mutableStateOf<Article?>(null)
    val favoriteState = mutableStateOf(false)

    init {
        compositeDisposable.add(
            articlesUseCase.findArticle(articleUuid)
                .subscribeOn(Schedulers.io())
                .flatMap(
                    { article -> articlesUseCase.getIsArticleFavorited(article) },
                    { article, isFavorite -> article to isFavorite })
                .subscribeOn(uiScheduler)
                .subscribe { articleAndFavoritePair ->
                    articleState.value = articleAndFavoritePair.first
                    favoriteState.value = articleAndFavoritePair.second
                }
        )
    }

    fun toggleFavorite() {
        articleState.value?.let { article ->
            compositeDisposable.add(
                articlesUseCase.toggleFavorite(article)
                    .subscribeOn(Schedulers.io())
                    .subscribe { isFavorited ->
                        favoriteState.value = isFavorited
                    }
            )
        }
    }

    override fun onCleared() {
        compositeDisposable.dispose()
        super.onCleared()
    }

    companion object {
        const val ARTICLE_UUID_KEY = "articleUuid"
    }
}