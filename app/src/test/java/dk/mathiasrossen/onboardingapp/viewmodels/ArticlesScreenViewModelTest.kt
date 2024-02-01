package dk.mathiasrossen.onboardingapp.viewmodels

import androidx.lifecycle.SavedStateHandle
import dk.mathiasrossen.onboardingapp.api.NewsApiService
import dk.mathiasrossen.onboardingapp.data.article.Article
import dk.mathiasrossen.onboardingapp.ui.articles.list.ArticlesScreenViewModel
import dk.mathiasrossen.onboardingapp.ui.articles.list.SortState
import dk.mathiasrossen.onboardingapp.use_cases.ArticlesUseCase
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
    private val appBarTitle = "appBarTitle"
    private val date = "2024-01-18"
    private val sampleUrl = "horse"
    private val sortBy = NewsApiService.SORT_BY_POPULAR
    private val useCase = mock<ArticlesUseCase>()
    private val scheduler = Schedulers.trampoline()
    private val dateUtils = mock<DateUtils>()
    private lateinit var viewModel: ArticlesScreenViewModel
    private val savedStateHandle = SavedStateHandle().apply {
        set(ArticlesScreenViewModel.SOURCE_ID_KEY, sourceId)
        set(ArticlesScreenViewModel.SOURCE_NAME_KEY, appBarTitle)
    }
    private val mockArticles = listOf(
        Article.createSample(sampleUrl),
        Article.createSample()
    )

    @Before
    fun setup() {
        given(dateUtils.oneDayPast()).willReturn(LocalDate.parse(date))
        given(useCase.getArticles(sourceId, sortBy, date)).willReturn(
            Single.just(mockArticles)
        )
        given(useCase.getFavoriteArticles()).willReturn(
            Single.just(listOf())
        )
        viewModel = createViewModel()
    }

    @Test
    fun init_appBarTitleIsSet() {
        Assert.assertEquals(appBarTitle, viewModel.appBarTitle.value)
    }

    @Test
    fun init_serviceGetsArticles() {
        verify(useCase).getArticles(sourceId, sortBy, date)
    }

    @Test
    fun init_serviceGetsFavoriteArticles() {
        verify(useCase).getFavoriteArticles()
    }

    @Test
    fun init_onSuccess_articlesReturned() {
        Assert.assertEquals(mockArticles, viewModel.articles.keys.toList())
    }

    @Test
    fun sortByPopularToday_sortStateEqualsPopularToday() {
        viewModel.setSortState(SortState.POPULAR_TODAY)

        Assert.assertEquals(SortState.POPULAR_TODAY, viewModel.sortState.value)
    }

    @Test
    fun sortByPopularAll_sortStateEqualsPopularAll() {
        given(
            useCase.getArticles(
                sourceId,
                NewsApiService.SORT_BY_POPULAR,
                null
            )
        ).willReturn(
            Single.just(mockArticles)
        )

        viewModel.setSortState(SortState.POPULAR_ALL_TIME)

        Assert.assertEquals(SortState.POPULAR_ALL_TIME, viewModel.sortState.value)
    }

    @Test
    fun sortByNewest_sortStateEqualsNewest() {
        given(
            useCase.getArticles(
                sourceId,
                NewsApiService.SORT_BY_PUBLISHED_AT,
                null
            )
        ).willReturn(
            Single.just(mockArticles)
        )

        viewModel.setSortState(SortState.NEWEST)

        Assert.assertEquals(SortState.NEWEST, viewModel.sortState.value)
    }

    @Test
    fun init_articleIsNotFavorited() {
        Assert.assertFalse(viewModel.articles.values.first().value)
    }

    @Test
    fun toggleFavorite_articleIsNowFavorited() {
        val article = mockArticles.first()
        given(useCase.toggleFavorite(article)).willReturn(
            Single.just(true)
        )

        viewModel.toggleFavorite(article)

        Assert.assertTrue(viewModel.articles.values.first().value)
    }

    private fun createViewModel() = ArticlesScreenViewModel(useCase, scheduler, scheduler, dateUtils, savedStateHandle)
}