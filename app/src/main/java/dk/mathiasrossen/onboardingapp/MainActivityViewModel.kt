package dk.mathiasrossen.onboardingapp

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import dk.mathiasrossen.onboardingapp.dependency_injection.DataStoreModule
import kotlinx.coroutines.flow.map
import javax.inject.Inject

@HiltViewModel
class MainActivityViewModel @Inject constructor(dataStore: DataStore<Preferences>) : ViewModel() {
    private val _isTutorialCompleted = MutableLiveData(false)
    val isTutorialCompleted: LiveData<Boolean> = _isTutorialCompleted

    init {
        val key  = booleanPreferencesKey(DataStoreModule.KEY_TUTORIAL_COMPLETED)
        dataStore.data.map { preferences ->
            _isTutorialCompleted.postValue(preferences[key] ?: false)
        }
    }
}
