package dk.mathiasrossen.onboardingapp.ui.sources

import androidx.compose.runtime.mutableStateOf
import dagger.hilt.android.lifecycle.HiltViewModel
import dk.mathiasrossen.onboardingapp.R
import dk.mathiasrossen.onboardingapp.api.NewsApiService
import dk.mathiasrossen.onboardingapp.api.response_models.NewsSourcesResponse
import dk.mathiasrossen.onboardingapp.dependency_injection.annotations.UiScheduler
import dk.mathiasrossen.onboardingapp.utils.BaseViewModel
import dk.mathiasrossen.onboardingapp.utils.errors.ErrorActionBus
import dk.mathiasrossen.onboardingapp.utils.errors.RetryActionError
import io.reactivex.rxjava3.core.Scheduler
import javax.inject.Inject

@HiltViewModel
class SourcesViewModel @Inject constructor(
    private val newsApiService: NewsApiService,
    @UiScheduler
    private val uiScheduler: Scheduler,
    private val errorActionBus: ErrorActionBus
) : BaseViewModel() {
    val newsSources = mutableStateOf(listOf<NewsSourcesResponse.NewsSource>())

    init {
        listenToErrorAction()
        loadSources()
    }

    private fun loadSources() {
        isLoading.value = true
        compositeDisposable.add(
            newsApiService.getSources()
                .observeOn(uiScheduler)
                .doFinally { isLoading.value = false }
                .subscribe({ newsSourcesResponse ->
                    newsSources.value = newsSourcesResponse.sourcesSorted
                }) {
                    errorPromoter.submitError(RetryActionError(messageStringResource = R.string.sources_error))
                }
        )
    }

    private fun listenToErrorAction() {
        compositeDisposable.add(
            errorActionBus.listenErrorAction()
                .observeOn(uiScheduler)
                .subscribe({ loadSources() }) {}
        )
    }

    fun onResume() {
        if (!isLoading.value && newsSources.value.isEmpty()) {
            loadSources()
        }
    }
}
