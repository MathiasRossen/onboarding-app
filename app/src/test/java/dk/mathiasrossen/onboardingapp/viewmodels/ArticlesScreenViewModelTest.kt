package dk.mathiasrossen.onboardingapp.viewmodels

import androidx.lifecycle.SavedStateHandle
import dk.mathiasrossen.onboardingapp.api.NewsApiService
import dk.mathiasrossen.onboardingapp.api.response_models.ArticlesResponse
import dk.mathiasrossen.onboardingapp.models.Article
import dk.mathiasrossen.onboardingapp.ui.articles.list.ArticlesScreenViewModel
import dk.mathiasrossen.onboardingapp.ui.articles.list.SortState
import dk.mathiasrossen.onboardingapp.utils.date.DateUtils
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.schedulers.Schedulers
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.mockito.kotlin.given
import org.mockito.kotlin.mock
import org.mockito.kotlin.verify
import java.time.LocalDate

class ArticlesScreenViewModelTest {
    private val sourceId = "123"
    private val date = "2024-01-18"
    private val language = NewsApiService.DEFAULT_LANGUAGE
    private val sortBy = NewsApiService.SORT_BY_POPULAR
    private val service = mock<NewsApiService>()
    private val scheduler = Schedulers.trampoline()
    private val dateUtils = mock<DateUtils>()
    private lateinit var viewModel: ArticlesScreenViewModel
    private val savedStateHandle = SavedStateHandle().apply {
        set(ArticlesScreenViewModel.SOURCE_ID_KEY, sourceId)
    }
    private val mockArticleResponse = ArticlesResponse(
        "ok",
        2,
        listOf(
            Article.createSample(),
            Article.createSample()
        )
    )

    @Before
    fun setup() {
        given(dateUtils.oneDayPast()).willReturn(LocalDate.parse(date))
        given(service.getArticlesFromSource(sourceId, language, sortBy, date)).willReturn(
            Single.just(mockArticleResponse)
        )
        viewModel = createViewModel()
    }

    @Test
    fun init_serviceGetsArticles() {
        verify(service).getArticlesFromSource(sourceId, from = date)
    }

    @Test
    fun init_onSuccess_articlesReturned() {
        Assert.assertEquals(mockArticleResponse.articles, viewModel.articles.value)
    }

    @Test
    fun sortByPopularToday_sortStateEqualsPopularToday() {
        viewModel.setSortState(SortState.POPULAR_TODAY)

        Assert.assertEquals(SortState.POPULAR_TODAY, viewModel.sortState.value)
    }

    @Test
    fun sortByPopularAll_sortStateEqualsPopularAll() {
        given(
            service.getArticlesFromSource(
                sourceId,
                language,
                NewsApiService.SORT_BY_POPULAR,
                null
            )
        ).willReturn(
            Single.just(mockArticleResponse)
        )

        viewModel.setSortState(SortState.POPULAR_ALL_TIME)

        Assert.assertEquals(SortState.POPULAR_ALL_TIME, viewModel.sortState.value)
    }

    @Test
    fun sortByNewest_sortStateEqualsNewest() {
        given(
            service.getArticlesFromSource(
                sourceId,
                language,
                NewsApiService.SORT_BY_PUBLISHED_AT,
                null
            )
        ).willReturn(
            Single.just(mockArticleResponse)
        )

        viewModel.setSortState(SortState.NEWEST)

        Assert.assertEquals(SortState.NEWEST, viewModel.sortState.value)
    }

    private fun createViewModel() = ArticlesScreenViewModel(service, scheduler, dateUtils, savedStateHandle)
}