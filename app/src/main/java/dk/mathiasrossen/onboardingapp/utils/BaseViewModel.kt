package dk.mathiasrossen.onboardingapp.utils

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import io.reactivex.rxjava3.disposables.CompositeDisposable

open class BaseViewModel(title: String = "NewsApp"): ViewModel() {
    val appBarTitle = mutableStateOf(title)

    protected var compositeDisposable = CompositeDisposable()

    override fun onCleared() {
        compositeDisposable.dispose()
        super.onCleared()
    }
}