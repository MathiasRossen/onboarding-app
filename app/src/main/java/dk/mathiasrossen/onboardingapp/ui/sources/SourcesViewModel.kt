package dk.mathiasrossen.onboardingapp.ui.sources

import androidx.compose.runtime.mutableStateOf
import dagger.hilt.android.lifecycle.HiltViewModel
import dk.mathiasrossen.onboardingapp.api.NewsApiService
import dk.mathiasrossen.onboardingapp.api.response_models.NewsSourcesResponse
import dk.mathiasrossen.onboardingapp.dependency_injection.annotations.UiScheduler
import dk.mathiasrossen.onboardingapp.utils.BaseViewModel
import io.reactivex.rxjava3.core.Scheduler
import javax.inject.Inject

@HiltViewModel
class SourcesViewModel @Inject constructor(
    private val newsApiService: NewsApiService, @UiScheduler private val uiScheduler: Scheduler
) : BaseViewModel() {
    val isLoading = mutableStateOf(true)
    val hasError = mutableStateOf(false)
    val newsSources = mutableStateOf(listOf<NewsSourcesResponse.NewsSource>())

    init {
        loadSources()
    }

    fun loadSources() {
        isLoading.value = true
        compositeDisposable.add(
            newsApiService.getSources().observeOn(uiScheduler).subscribe({ newsSourcesResponse ->
                newsSources.value = newsSourcesResponse.sourcesSorted
                isLoading.value = false
                hasError.value = false
            }) {
                isLoading.value = false
                hasError.value = true
            }
        )
    }
}
