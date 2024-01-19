package dk.mathiasrossen.onboardingapp.viewmodels

import androidx.lifecycle.SavedStateHandle
import dk.mathiasrossen.onboardingapp.api.NewsApiService
import dk.mathiasrossen.onboardingapp.api.response_models.ArticlesResponse
import dk.mathiasrossen.onboardingapp.models.Article
import dk.mathiasrossen.onboardingapp.ui.articles.ArticleListViewModel
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.schedulers.Schedulers
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.mockito.kotlin.any
import org.mockito.kotlin.eq
import org.mockito.kotlin.given
import org.mockito.kotlin.mock
import org.mockito.kotlin.verify

class ArticleListViewModelTest {
    private val sourceId = "123"
    private val date = "2024-01-18"
    private val service = mock<NewsApiService>()
    private val scheduler = Schedulers.trampoline()
    private lateinit var viewModel: ArticleListViewModel
    private val savedStateHandle = SavedStateHandle().apply {
        set(ArticleListViewModel.SOURCE_ID_KEY, sourceId)
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
        given(service.getArticlesFromSource(eq(sourceId), any(), any(), eq(date))).willReturn(
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

    private fun createViewModel() = ArticleListViewModel(service, scheduler, savedStateHandle)
}