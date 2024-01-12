package dk.mathiasrossen.onboardingapp.ui.sources

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import dk.mathiasrossen.onboardingapp.api.NewsApiService
import dk.mathiasrossen.onboardingapp.models.NewsSource
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.disposables.Disposable
import javax.inject.Inject

class SourcesScreenViewModel @Inject constructor(newsApiService: NewsApiService) : ViewModel() {
    private var disposable = Disposable.disposed()

    var newsSources = mutableStateOf(listOf<NewsSource>())
        private set

    init {
        disposable = newsApiService.getSources()
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({ newsSourcesResponse ->
                newsSources.value = newsSourcesResponse.sourcesSorted
            }) { /* error */ }
    }

    override fun onCleared() {
        disposable.dispose()
        super.onCleared()
    }
}