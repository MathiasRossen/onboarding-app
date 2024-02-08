package dk.mathiasrossen.onboardingapp.viewmodels

import dk.mathiasrossen.onboardingapp.api.NewsApiService
import dk.mathiasrossen.onboardingapp.api.response_models.NewsSourcesResponse
import dk.mathiasrossen.onboardingapp.ui.sources.SourcesViewModel
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.schedulers.Schedulers
import org.junit.Assert.assertEquals
import org.junit.Test
import org.mockito.BDDMockito.given
import org.mockito.kotlin.mock
import org.mockito.kotlin.times
import org.mockito.kotlin.verify

class SourcesViewModelTest {
    private val service = mock<NewsApiService>()
    private val scheduler = Schedulers.trampoline()
    private lateinit var viewModel: SourcesViewModel

    @Test
    fun init_onSuccess_newsSourcesReturned() {
        val mockNewsSourcesResponse = NewsSourcesResponse(
            "ok",
            listOf(
                createNewsSource("1", "HorseNews"),
                createNewsSource("2", "DonkeyNews")
            )
        )
        given(service.getSources()).willReturn(Single.just(mockNewsSourcesResponse))

        viewModel = createViewModel()

        assertEquals(mockNewsSourcesResponse.sourcesSorted, viewModel.newsSources.value)
    }

    @Test
    fun init_serviceGetsSources() {
        given(service.getSources()).willReturn(Single.just(NewsSourcesResponse("", emptyList())))

        viewModel = createViewModel()

        verify(service).getSources()
    }

    @Test
    fun onResume_listIsEmpty_shouldLoadArticles() {
        given(service.getSources()).willReturn(Single.just(NewsSourcesResponse("", emptyList())))

        viewModel = createViewModel()
        viewModel.onResume()

        verify(service, times(2)).getSources()
    }

    @Test
    fun onResume_listIsNotEmpty_shouldNotLoadArticles() {
        val mockNewsSourcesResponse = NewsSourcesResponse(
            "ok",
            listOf(
                createNewsSource("1", "HorseNews"),
                createNewsSource("2", "DonkeyNews")
            )
        )
        given(service.getSources()).willReturn(Single.just(mockNewsSourcesResponse))

        viewModel = createViewModel()
        viewModel.onResume()

        verify(service, times(1)).getSources()
    }

    private fun createViewModel() = SourcesViewModel(service, scheduler)

    private fun createNewsSource(id: String, name: String): NewsSourcesResponse.NewsSource {
        return NewsSourcesResponse.NewsSource(
            id,
            name,
            "Your trusted source for breaking news, analysis, exclusive interviews, headlines, and videos related to horses at Horsenews.com",
            "https://horsenews.com/1",
            "horses",
            "en",
            "us"
        )
    }
}
