package dk.mathiasrossen.onboardingapp.viewmodels

import dk.mathiasrossen.onboardingapp.data.article.Article
import dk.mathiasrossen.onboardingapp.data.article.FavoriteArticle
import dk.mathiasrossen.onboardingapp.ui.favorites.FavoritesViewModel
import dk.mathiasrossen.onboardingapp.use_cases.ArticlesUseCase
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.schedulers.Schedulers
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito.mock
import org.mockito.kotlin.given

class FavoritesViewModelTest {
    private val scheduler = Schedulers.trampoline()
    private val useCase = mock<ArticlesUseCase>()
    private val articleUrl = "horse"
    private val mockArticles = listOf(Article.createSample(articleUrl), Article.createSample())
    private val mockFavoriteArticles = listOf(FavoriteArticle(articleUrl))
    private lateinit var viewModel: FavoritesViewModel

    @Before
    fun setup() {
        given(useCase.getFavoriteArticles()).willReturn(Single.just(mockFavoriteArticles))
        given(useCase.getArticlesByUrls(listOf(articleUrl))).willReturn(Single.just(mockArticles))
        viewModel = createViewModel()
    }

    @Test
    fun onResume_favoriteArticlesAreLoaded() {
        viewModel.onResume()

        assertEquals(mockArticles, viewModel.articles.value)
    }

    @Test
    fun toggleFavorite_articleIsRemovedFromList() {
        given(useCase.toggleFavorite(mockArticles.first())).willReturn(Single.just(false))

        viewModel.onResume()
        viewModel.toggleFavorite(mockArticles.first())

        assertEquals(listOf(mockArticles.last()), viewModel.articles.value)
    }

    private fun createViewModel() = FavoritesViewModel(useCase, scheduler)
}
