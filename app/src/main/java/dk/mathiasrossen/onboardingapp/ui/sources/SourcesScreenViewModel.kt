package dk.mathiasrossen.onboardingapp.ui.sources

import androidx.lifecycle.ViewModel
import dk.mathiasrossen.onboardingapp.api.newsApiService
import dk.mathiasrossen.onboardingapp.models.NewsSource
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class SourcesScreenViewModel : ViewModel() {
    private val _newsSources = MutableStateFlow(listOf<NewsSource>())

    val newsSources = _newsSources.asStateFlow()

    fun fetchSources() {
        newsApiService.getSources()
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({ newsSourcesResponse ->
                _newsSources.value = newsSourcesResponse.sourcesSorted
            }) { /* error */ }
    }
}