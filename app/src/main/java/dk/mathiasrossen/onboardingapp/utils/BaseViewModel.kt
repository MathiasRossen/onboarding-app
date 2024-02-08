package dk.mathiasrossen.onboardingapp.utils

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import dk.mathiasrossen.onboardingapp.utils.errors.ErrorPromoter
import io.reactivex.rxjava3.disposables.CompositeDisposable
import javax.inject.Inject

@HiltViewModel
open class BaseViewModel(title: String) : ViewModel() {
    @Inject
    constructor() : this("NewsApp")

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
