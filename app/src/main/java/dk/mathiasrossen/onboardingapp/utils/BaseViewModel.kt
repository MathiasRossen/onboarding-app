package dk.mathiasrossen.onboardingapp.utils

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import dk.mathiasrossen.onboardingapp.utils.errors.ErrorPromoter
import io.reactivex.rxjava3.disposables.CompositeDisposable
import javax.inject.Inject

abstract class BaseViewModel(title: String = "NewsApp") : ViewModel() {
    @Inject
    lateinit var errorPromoter: ErrorPromoter
    val appBarTitle = mutableStateOf(title)
    val isLoading = mutableStateOf(false)

    protected var compositeDisposable = CompositeDisposable()

    override fun onCleared() {
        compositeDisposable.dispose()
        super.onCleared()
    }
}
