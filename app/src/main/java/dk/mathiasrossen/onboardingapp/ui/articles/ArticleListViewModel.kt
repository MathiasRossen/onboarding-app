package dk.mathiasrossen.onboardingapp.ui.articles

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import dk.mathiasrossen.onboardingapp.api.NewsApiService
import dk.mathiasrossen.onboardingapp.models.Article
import io.reactivex.rxjava3.core.Scheduler
import io.reactivex.rxjava3.disposables.Disposable
import org.joda.time.LocalDate
import javax.inject.Inject

@HiltViewModel
class ArticleListViewModel @Inject constructor(
    private val newsApiService: NewsApiService,
    private val uiScheduler: Scheduler,
    savedStateHandle: SavedStateHandle
) : ViewModel() {
    private val sourceId: String = checkNotNull(savedStateHandle[SOURCE_ID_KEY])
    private var disposable = Disposable.disposed()

    var articles = mutableStateOf(listOf<Article>())
        private set

    var isRefreshing = mutableStateOf(false)
        private set

    var sortState = mutableStateOf(SortState.POPULAR_TODAY)
        private set

    init {
        refresh()
    }

    fun refresh() {
        isRefreshing.value = true

        val sortBy =
            if (sortState.value == SortState.POPULAR_TODAY || sortState.value == SortState.POPULAR_ALL_TIME) NewsApiService.SORT_BY_POPULAR else NewsApiService.SORT_BY_PUBLISHED_AT
        val yesterday = LocalDate().minusDays(1)
        val from = if (sortState.value == SortState.POPULAR_TODAY) yesterday.toString() else null

        disposable = newsApiService.getArticlesFromSource(sourceId, sortBy = sortBy, from = from)
            .observeOn(uiScheduler)
            .subscribe({ articlesResponse ->
                articles.value = articlesResponse.articles
                isRefreshing.value = false
            }) { /* error */ }
    }

    fun sortByPopularToday() {
        sortState.value = SortState.POPULAR_TODAY
        refresh()
    }

    fun sortByPopularAllTime() {
        sortState.value = SortState.POPULAR_ALL_TIME
        refresh()
    }

    fun sortByNewest() {
        sortState.value = SortState.NEWEST
        refresh()
    }

    override fun onCleared() {
        disposable.dispose()
        super.onCleared()
    }

    companion object {
        private const val SOURCE_ID_KEY = "sourceId"
    }
}