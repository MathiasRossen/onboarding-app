package dk.mathiasrossen.onboardingapp

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import dk.mathiasrossen.onboardingapp.dependency_injection.DataStoreModule
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainActivityViewModel @Inject constructor(private val dataStore: DataStore<Preferences>) : ViewModel() {
    var isTutorialCompleted by mutableStateOf(false)
        private set

    init {
        viewModelScope.launch {
            val preferences = dataStore.data.first()
            val key = booleanPreferencesKey(DataStoreModule.KEY_TUTORIAL_COMPLETED)
            isTutorialCompleted = preferences[key] ?: false
        }
    }

    fun completeTutorial() {
        viewModelScope.launch {
            dataStore.edit { settings ->
                val key = booleanPreferencesKey(DataStoreModule.KEY_TUTORIAL_COMPLETED)
                settings[key] = true
                isTutorialCompleted = true
            }
        }
    }
}
