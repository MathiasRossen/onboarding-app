package dk.mathiasrossen.onboardingapp.ui.articles.list

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import dagger.hilt.android.lifecycle.HiltViewModel
import dk.mathiasrossen.onboardingapp.R
import dk.mathiasrossen.onboardingapp.api.NewsApiService
import dk.mathiasrossen.onboardingapp.data.article.Article
import dk.mathiasrossen.onboardingapp.dependency_injection.annotations.IoScheduler
import dk.mathiasrossen.onboardingapp.dependency_injection.annotations.UiScheduler
import dk.mathiasrossen.onboardingapp.use_cases.ArticlesUseCase
import dk.mathiasrossen.onboardingapp.utils.BaseViewModel
import dk.mathiasrossen.onboardingapp.utils.date.DateUtils
import dk.mathiasrossen.onboardingapp.utils.errors.RetryActionError
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
) : BaseViewModel() {
    private val sourceId: String = savedStateHandle[SOURCE_ID_KEY] ?: ""
    private val sourceName: String = savedStateHandle[SOURCE_NAME_KEY] ?: ""
    val sortState = mutableStateOf(SortState.POPULAR_TODAY)

    var articles = mapOf<Article, MutableState<Boolean>>()
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
        isLoading.value = true
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
            )
                .doFinally { isLoading.value = false }
                .subscribe({ articleList ->
                    articles = articleList
                }) {
                    errorPromoter.submitError(RetryActionError(messageStringResource = R.string.articles_error))
                }
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
                .subscribe({ isFavorite ->
                    articles[article]?.value = isFavorite
                }) { errorPromoter.submitGenericError() }
        )
    }

    companion object {
        const val SOURCE_ID_KEY = "sourceId"
        const val SOURCE_NAME_KEY = "sourceName"
    }
}
