package dk.mathiasrossen.onboardingapp.ui.sources

import androidx.compose.runtime.mutableStateOf
import dagger.hilt.android.lifecycle.HiltViewModel
import dk.mathiasrossen.onboardingapp.R
import dk.mathiasrossen.onboardingapp.api.NewsApiService
import dk.mathiasrossen.onboardingapp.api.response_models.NewsSourcesResponse
import dk.mathiasrossen.onboardingapp.dependency_injection.annotations.UiScheduler
import dk.mathiasrossen.onboardingapp.utils.BaseViewModel
import dk.mathiasrossen.onboardingapp.utils.errors.ErrorPromoter
import dk.mathiasrossen.onboardingapp.utils.errors.RetryActionError
import io.reactivex.rxjava3.core.Scheduler
import javax.inject.Inject

@HiltViewModel
class SourcesViewModel @Inject constructor(
    private val newsApiService: NewsApiService,
    @UiScheduler
    private val uiScheduler: Scheduler,
    private val errorPromoter: ErrorPromoter
) : BaseViewModel() {
    val newsSources = mutableStateOf(listOf<NewsSourcesResponse.NewsSource>())

    init {
        loadSources()
    }

    private fun loadSources() {
        isLoading.value = true
        compositeDisposable.add(
            newsApiService.getSources().observeOn(uiScheduler).subscribe({ newsSourcesResponse ->
                newsSources.value = newsSourcesResponse.sourcesSorted
                isLoading.value = false
            }) {
                isLoading.value = false
                errorPromoter.submitError(
                    RetryActionError(
                        messageStringResource = R.string.sources_error,
                        retryAction = ::loadSources
                    )
                )
            }
        )
    }

    fun onResume() {
        if (!isLoading.value && newsSources.value.isEmpty()) {
            loadSources()
        }
    }
}
