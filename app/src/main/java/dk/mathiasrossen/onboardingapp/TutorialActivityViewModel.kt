package dk.mathiasrossen.onboardingapp

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import dk.mathiasrossen.onboardingapp.dependency_injection.DataStoreModule
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TutorialActivityViewModel @Inject constructor(private val dataStore: DataStore<Preferences>) : ViewModel() {
    fun completeTutorial(afterKeyUpdate: () -> Unit) {
        viewModelScope.launch {
            dataStore.edit { settings ->
                val key = booleanPreferencesKey(DataStoreModule.KEY_TUTORIAL_COMPLETED)
                settings[key] = true
                afterKeyUpdate()
            }
        }
    }
}