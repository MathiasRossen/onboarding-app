package dk.mathiasrossen.onboardingapp

import android.content.Context
import android.os.Bundle
import androidx.activity.addCallback
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import dk.mathiasrossen.onboardingapp.ui.tutorial.TutorialPageOne
import dk.mathiasrossen.onboardingapp.ui.tutorial.TutorialPageThree
import dk.mathiasrossen.onboardingapp.ui.tutorial.TutorialPageTwo
import dk.mathiasrossen.onboardingapp.utils.dataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch

class TutorialActivity : AppCompatActivity() {
    private val pages = 3

    @OptIn(ExperimentalFoundationApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        onBackPressedDispatcher.addCallback { /* Prevent user from popping the tutorial screen */ }

        setContent {
            val pagerState = rememberPagerState { pages }
            HorizontalPager(state = pagerState, modifier = Modifier.fillMaxSize()) { page ->
                val coroutineScope = rememberCoroutineScope()
                when(page) {
                    0 -> TutorialPageOne {
                        coroutineScope.launch {
                            pagerState.scrollToNextPage()
                        }
                    }
                    1 -> TutorialPageTwo {
                        coroutineScope.launch {
                            pagerState.scrollToNextPage()
                        }
                    }
                    2 -> TutorialPageThree {
                        coroutineScope.launch {
                            completeTutorial()
                        }
                    }
                }
            }
        }
    }

    private suspend fun completeTutorial() {
        dataStore.edit { settings ->
            val key = booleanPreferencesKey(TUTORIAL_COMPLETED)
            settings[key] = true
        }
        finish()
    }

    @OptIn(ExperimentalFoundationApi::class)
    private suspend fun PagerState.scrollToNextPage() {
        animateScrollToPage(currentPage + 1)
    }

    companion object {
        private const val TUTORIAL_COMPLETED = "tutorial_completed"

        fun isTutorialCompleted(context: Context): Flow<Boolean> {
            val key = booleanPreferencesKey(TUTORIAL_COMPLETED)
            return context.dataStore.data.map { preferences ->
                preferences[key] ?: false
            }
        }
    }
}