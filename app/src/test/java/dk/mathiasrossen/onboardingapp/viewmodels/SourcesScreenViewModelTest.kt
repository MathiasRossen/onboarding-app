package dk.mathiasrossen.onboardingapp.viewmodels

import dk.mathiasrossen.onboardingapp.api.NewsApiService
import dk.mathiasrossen.onboardingapp.api.response_models.NewsSourcesResponse
import dk.mathiasrossen.onboardingapp.models.NewsSource
import dk.mathiasrossen.onboardingapp.ui.sources.SourcesScreenViewModel
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.schedulers.Schedulers
import org.junit.Assert
import org.junit.Test
import org.mockito.BDDMockito.given
import org.mockito.kotlin.mock
import org.mockito.kotlin.verify

class SourcesScreenViewModelTest {
    private val service = mock<NewsApiService>()
    private val scheduler = Schedulers.trampoline()
    private lateinit var viewModel: SourcesScreenViewModel

    @Test
    fun init_onSucess_newsSourcesReturned() {
        val mockNewsSourcesResponse = NewsSourcesResponse(
            "ok",
            listOf(
                createNewsSource("1", "HorseNews"),
                createNewsSource("2", "DonkeyNews")
            )
        )
        given(service.getSources()).willReturn(Single.just(mockNewsSourcesResponse))

        viewModel = createViewModel()

        Assert.assertEquals(mockNewsSourcesResponse.sourcesSorted, viewModel.newsSources.value)
    }

    @Test
    fun init_serviceGetsSources() {
        given(service.getSources()).willReturn(Single.just(NewsSourcesResponse("", emptyList())))

        viewModel = createViewModel()

        verify(service).getSources()
    }

    private fun createViewModel() = SourcesScreenViewModel(service, scheduler)

    private fun createNewsSource(id: String, name: String): NewsSource {
        return NewsSource(
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