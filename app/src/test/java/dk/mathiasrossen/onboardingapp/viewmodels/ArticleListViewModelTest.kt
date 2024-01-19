package dk.mathiasrossen.onboardingapp.viewmodels

import androidx.lifecycle.SavedStateHandle
import dk.mathiasrossen.onboardingapp.api.NewsApiService
import dk.mathiasrossen.onboardingapp.api.response_models.ArticlesResponse
import dk.mathiasrossen.onboardingapp.ui.articles.ArticleListViewModel
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.schedulers.Schedulers
import org.junit.Test
import org.mockito.kotlin.given
import org.mockito.kotlin.mock
import org.mockito.kotlin.verify

class ArticleListViewModelTest {
    private val sourceId = "123"
    private val service = mock<NewsApiService>()
    private val scheduler = Schedulers.trampoline()
    private lateinit var viewModel: ArticleListViewModel
    private val savedStateHandle = SavedStateHandle().apply {
        set(ArticleListViewModel.SOURCE_ID_KEY, sourceId)
    }

    @Test
    fun init_serviceGetsArticles() {
        given(service.getArticlesFromSource(sourceId)).willReturn(
            Single.just(ArticlesResponse("", 0, emptyList()))
        )

        viewModel = createViewModel()

        verify(service).getArticlesFromSource(sourceId)
    }

    private fun createViewModel() = ArticleListViewModel(service, scheduler, savedStateHandle)
}