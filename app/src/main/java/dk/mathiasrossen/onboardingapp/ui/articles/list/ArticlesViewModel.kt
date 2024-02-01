package dk.mathiasrossen.onboardingapp.ui.articles.list

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import dagger.hilt.android.lifecycle.HiltViewModel
import dk.mathiasrossen.onboardingapp.api.NewsApiService
import dk.mathiasrossen.onboardingapp.data.article.Article
import dk.mathiasrossen.onboardingapp.dependency_injection.annotations.IoScheduler
import dk.mathiasrossen.onboardingapp.dependency_injection.annotations.UiScheduler
import dk.mathiasrossen.onboardingapp.use_cases.ArticlesUseCase
import dk.mathiasrossen.onboardingapp.utils.OnboardingViewModel
import dk.mathiasrossen.onboardingapp.utils.date.DateUtils
import io.reactivex.rxjava3.core.Scheduler
import io.reactivex.rxjava3.core.Single
import javax.inject.Inject

@HiltViewModel
class ArticlesViewModel @Inject constructor(
    private val articlesUseCase: ArticlesUseCase,
    @UiScheduler
    private val uiScheduler: Scheduler,
    @IoScheduler
    private val ioScheduler: Scheduler,
    private val dateUtils: DateUtils,
    savedStateHandle: SavedStateHandle
) : OnboardingViewModel() {
    private val sourceId: String = checkNotNull(savedStateHandle[SOURCE_ID_KEY])
    private val sourceName: String = checkNotNull(savedStateHandle[SOURCE_NAME_KEY])

    var articles = mapOf<Article, MutableState<Boolean>>()
        private set

    var isRefreshing = mutableStateOf(false)
        private set

    var sortState = mutableStateOf(SortState.POPULAR_TODAY)
        private set

    private val sortBy
        get() = if (sortState.value == SortState.POPULAR_TODAY || sortState.value == SortState.POPULAR_ALL_TIME) {
            NewsApiService.SORT_BY_POPULAR
        } else {
            NewsApiService.SORT_BY_PUBLISHED_AT
        }

    init {
        appBarTitle.value = sourceName
        refresh()
    }

    fun refresh() {
        isRefreshing.value = true

        compositeDisposable.add(
            getArticles().flatMap(
                { articlesUseCase.getFavoriteArticles() },
                { articleList, favoriteArticles ->
                    articleList.associateWith { article ->
                        val isFavorite =
                            favoriteArticles.any { favoriteArticle -> favoriteArticle.articleUrl == article.url }
                        mutableStateOf(isFavorite)
                    }
                }
            ).subscribe({ articleList ->
                articles = articleList
                isRefreshing.value = false
            }) { /* error */ }
        )
    }

    private fun getArticles(): Single<List<Article>> {
        val oneDayPast = dateUtils.oneDayPast().toString()
        val from = if (sortState.value == SortState.POPULAR_TODAY) oneDayPast else null
        return articlesUseCase.getArticles(sourceId, sortBy, from).subscribeOn(uiScheduler)
    }

    fun setSortState(state: SortState) {
        sortState.value = state
        refresh()
    }

    fun toggleFavorite(article: Article) {
        compositeDisposable.add(
            articlesUseCase.toggleFavorite(article)
                .subscribeOn(ioScheduler)
                .subscribe { isFavorite ->
                    articles[article]?.value = isFavorite
                }
        )
    }

    companion object {
        const val SOURCE_ID_KEY = "sourceId"
        const val SOURCE_NAME_KEY = "sourceName"
    }
}