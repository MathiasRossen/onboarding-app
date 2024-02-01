package dk.mathiasrossen.onboardingapp.viewmodels

import androidx.lifecycle.SavedStateHandle
import dk.mathiasrossen.onboardingapp.data.article.Article
import dk.mathiasrossen.onboardingapp.ui.articles.details.ArticleDetailsViewModel
import dk.mathiasrossen.onboardingapp.use_cases.ArticlesUseCase
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.schedulers.Schedulers
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.mockito.kotlin.given
import org.mockito.kotlin.mock
import org.mockito.kotlin.verify

class ArticleDetailsViewModelTest {
    private val scheduler = Schedulers.trampoline()
    private lateinit var viewModel: ArticleDetailsViewModel
    private val articleUuid = "1234"
    private val mockArticle = Article.createSample("horse")
    private val useCase = mock<ArticlesUseCase>()
    private val savedStateHandle = SavedStateHandle().apply {
        set(ArticleDetailsViewModel.ARTICLE_UUID_KEY, articleUuid)
    }

    @Before
    fun setup() {
        given(useCase.findArticle(articleUuid)).willReturn(
            Single.just(mockArticle)
        )
        given(useCase.getIsArticleFavorite(mockArticle)).willReturn(
            Single.just(false)
        )
        viewModel = createViewModel()
    }

    @Test
    fun init_articleIsFound() {
        verify(useCase).findArticle(articleUuid)
        verify(useCase).getIsArticleFavorite(mockArticle)
        Assert.assertNotNull(viewModel.articleState.value)
        Assert.assertEquals(mockArticle, viewModel.articleState.value)
        Assert.assertFalse(viewModel.favoriteState.value)
    }

    @Test
    fun toggleFavorite_articleIsNowFavorited() {
        given(useCase.toggleFavorite(mockArticle)).willReturn(
            Single.just(true)
        )

        viewModel.toggleFavorite()

        Assert.assertTrue(viewModel.favoriteState.value)
    }


    private fun createViewModel() = ArticleDetailsViewModel(useCase, scheduler, scheduler, savedStateHandle)
}