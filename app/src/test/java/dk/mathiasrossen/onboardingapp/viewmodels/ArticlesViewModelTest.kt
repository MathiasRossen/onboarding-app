package dk.mathiasrossen.onboardingapp.viewmodels

import androidx.lifecycle.SavedStateHandle
import dk.mathiasrossen.onboardingapp.api.NewsApiService
import dk.mathiasrossen.onboardingapp.data.article.Article
import dk.mathiasrossen.onboardingapp.ui.articles.list.ArticlesViewModel
import dk.mathiasrossen.onboardingapp.ui.articles.list.SortState
import dk.mathiasrossen.onboardingapp.use_cases.ArticlesUseCase
import dk.mathiasrossen.onboardingapp.utils.date.DateUtils
import dk.mathiasrossen.onboardingapp.utils.errors.ErrorActionBus
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.schedulers.Schedulers
import io.reactivex.rxjava3.subjects.PublishSubject
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import org.mockito.kotlin.given
import org.mockito.kotlin.mock
import org.mockito.kotlin.times
import org.mockito.kotlin.verify
import java.time.LocalDate

class ArticlesViewModelTest {
    private val sourceId = "123"
    private val appBarTitle = "appBarTitle"
    private val date = "2024-01-18"
    private val sampleUrl = "horse"
    private val sortBy = NewsApiService.SORT_BY_POPULAR
    private val useCase = mock<ArticlesUseCase>()
    private val scheduler = Schedulers.trampoline()
    private val dateUtils = mock<DateUtils>()
    private val errorActionBus = mock<ErrorActionBus>()
    private lateinit var viewModel: ArticlesViewModel
    private val savedStateHandle = SavedStateHandle().apply {
        set(ArticlesViewModel.SOURCE_ID_KEY, sourceId)
        set(ArticlesViewModel.SOURCE_NAME_KEY, appBarTitle)
    }
    private val mockArticles = listOf(
        Article.createSample(sampleUrl), Article.createSample()
    )

    @Before
    fun setup() {
        given(dateUtils.oneDayPast()).willReturn(LocalDate.parse(date))
        given(useCase.getArticles(sourceId, sortBy, date)).willReturn(Single.just(mockArticles))
        given(useCase.getFavoriteArticles()).willReturn(Single.just(listOf()))
        given(errorActionBus.listenErrorAction()).willReturn(PublishSubject.create())
    }

    @Test
    fun init_appBarTitleIsSet() {
        viewModel = createViewModel()

        assertEquals(appBarTitle, viewModel.appBarTitle.value)
    }

    @Test
    fun init_serviceGetsArticles() {
        viewModel = createViewModel()

        verify(useCase).getArticles(sourceId, sortBy, date)
    }

    @Test
    fun init_serviceGetsFavoriteArticles() {
        viewModel = createViewModel()

        verify(useCase).getFavoriteArticles()
    }

    @Test
    fun init_onSuccess_articlesReturned() {
        viewModel = createViewModel()

        assertEquals(mockArticles, viewModel.articles.keys.toList())
    }

    @Test
    fun sortByPopularToday_sortStateEqualsPopularToday() {
        viewModel = createViewModel()
        viewModel.setSortState(SortState.POPULAR_TODAY)

        assertEquals(SortState.POPULAR_TODAY, viewModel.sortState.value)
    }

    @Test
    fun sortByPopularAll_sortStateEqualsPopularAll() {
        given(useCase.getArticles(sourceId, NewsApiService.SORT_BY_POPULAR, null))
            .willReturn(Single.just(mockArticles))

        viewModel = createViewModel()
        viewModel.setSortState(SortState.POPULAR_ALL_TIME)

        assertEquals(SortState.POPULAR_ALL_TIME, viewModel.sortState.value)
    }

    @Test
    fun sortByNewest_sortStateEqualsNewest() {
        given(useCase.getArticles(sourceId, NewsApiService.SORT_BY_PUBLISHED_AT, null))
            .willReturn(Single.just(mockArticles))

        viewModel = createViewModel()
        viewModel.setSortState(SortState.NEWEST)

        assertEquals(SortState.NEWEST, viewModel.sortState.value)
    }

    @Test
    fun init_articleIsNotFavorited() {
        viewModel = createViewModel()

        assertFalse(viewModel.articles.values.first().value)
    }

    @Test
    fun toggleFavorite_articleIsNowFavorited() {
        val article = mockArticles.first()
        given(useCase.toggleFavorite(article)).willReturn(Single.just(true))

        viewModel = createViewModel()
        viewModel.toggleFavorite(article)

        assertTrue(viewModel.articles.values.first().value)
    }

    @Test
    fun onErrorActionCalled_shouldLoadArticles() {
        given(errorActionBus.listenErrorAction()).willReturn(Observable.just(Unit))

        viewModel = createViewModel()

        verify(useCase, times(2)).getArticles(sourceId, sortBy, date)
    }

    private fun createViewModel() = ArticlesViewModel(
        useCase,
        scheduler,
        scheduler,
        dateUtils,
        errorActionBus,
        savedStateHandle
    )
}
