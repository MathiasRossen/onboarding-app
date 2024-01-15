package dk.mathiasrossen.onboardingapp.utils

import android.content.Context
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.rxjava3.rxPreferencesDataStore
import androidx.datastore.rxjava3.RxDataStore

val Context.dataStore: RxDataStore<Preferences> by rxPreferencesDataStore("store")
