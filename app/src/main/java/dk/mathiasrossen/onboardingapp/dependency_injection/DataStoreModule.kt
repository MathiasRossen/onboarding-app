package dk.mathiasrossen.onboardingapp.dependency_injection

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object DataStoreModule {
    const val KEY_TUTORIAL_COMPLETED = "tutorial_completed"

    private val Context.dataStore: DataStore<Preferences> by preferencesDataStore("store")

    @Provides
    fun provideDataStore(@ApplicationContext context: Context) = context.dataStore

}