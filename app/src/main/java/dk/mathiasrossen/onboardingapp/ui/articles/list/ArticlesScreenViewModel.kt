package dk.mathiasrossen.onboardingapp.ui.articles.list

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import dk.mathiasrossen.onboardingapp.api.NewsApiService
import dk.mathiasrossen.onboardingapp.models.Article
import dk.mathiasrossen.onboardingapp.utils.date.DateUtils
import io.reactivex.rxjava3.core.Scheduler
import io.reactivex.rxjava3.disposables.Disposable
import javax.inject.Inject

@HiltViewModel
class ArticlesScreenViewModel @Inject constructor(
    private val newsApiService: NewsApiService,
    private val uiScheduler: Scheduler,
    private val dateUtils: DateUtils,
    savedStateHandle: SavedStateHandle,
) : ViewModel() {
    private val sourceId: String = checkNotNull(savedStateHandle[SOURCE_ID_KEY])
    private var disposable = Disposable.disposed()

    var articles = mutableStateOf(listOf<Article>())
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
        refresh()
    }

    fun refresh() {
        isRefreshing.value = true

        val oneDayPast = dateUtils.oneDayPast().toString()
        val from = if (sortState.value == SortState.POPULAR_TODAY) oneDayPast else null

        disposable = newsApiService.getArticlesFromSource(
            sourceId,
            language = NewsApiService.DEFAULT_LANGUAGE,
            sortBy = sortBy,
            from = from
        )
            .observeOn(uiScheduler)
            .subscribe({ articlesResponse ->
                articles.value = articlesResponse.articles
                isRefreshing.value = false
            }) { /* error */ }
    }

    fun setSortState(state: SortState) {
        sortState.value = state
        refresh()
    }

    override fun onCleared() {
        disposable.dispose()
        super.onCleared()
    }

    companion object {
        const val SOURCE_ID_KEY = "sourceId"
    }
}