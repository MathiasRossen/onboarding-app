package dk.mathiasrossen.onboardingapp.ui.sources

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import dk.mathiasrossen.onboardingapp.api.NewsApiService
import dk.mathiasrossen.onboardingapp.api.response_models.NewsSourcesResponse
import dk.mathiasrossen.onboardingapp.dependency_injection.annotations.UiScheduler
import io.reactivex.rxjava3.core.Scheduler
import io.reactivex.rxjava3.disposables.Disposable
import javax.inject.Inject

@HiltViewModel
class SourcesViewModel @Inject constructor(newsApiService: NewsApiService, @UiScheduler uiScheduler: Scheduler) :
    ViewModel() {
    private var disposable = Disposable.disposed()

    var newsSources = mutableStateOf(listOf<NewsSourcesResponse.NewsSource>())
        private set

    init {
        disposable = newsApiService.getSources()
            .observeOn(uiScheduler)
            .subscribe({ newsSourcesResponse ->
                newsSources.value = newsSourcesResponse.sourcesSorted
            }) { /* error */ }
    }

    override fun onCleared() {
        disposable.dispose()
        super.onCleared()
    }
}