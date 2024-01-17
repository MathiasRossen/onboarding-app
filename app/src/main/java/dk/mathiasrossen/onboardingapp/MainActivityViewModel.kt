package dk.mathiasrossen.onboardingapp

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class MainActivityViewModel : ViewModel() {
    private val _isTutorialCompleted = MutableLiveData(false)
    val isTutorialCompleted: LiveData<Boolean> = _isTutorialCompleted
}