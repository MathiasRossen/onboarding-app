package dk.mathiasrossen.onboardingapp

import android.os.Bundle
import androidx.activity.addCallback
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import dagger.hilt.android.AndroidEntryPoint
import dk.mathiasrossen.onboardingapp.ui.tutorial.TutorialPageOne
import dk.mathiasrossen.onboardingapp.ui.tutorial.TutorialPageThree
import dk.mathiasrossen.onboardingapp.ui.tutorial.TutorialPageTwo
import kotlinx.coroutines.launch

@AndroidEntryPoint
class TutorialActivity : AppCompatActivity() {
    private val pages = 3

    @OptIn(ExperimentalFoundationApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        onBackPressedDispatcher.addCallback { /* Prevent user from popping the tutorial screen */ }

        val tutorialActivityViewModel: TutorialActivityViewModel by viewModels()
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
                        tutorialActivityViewModel.completeTutorial {
                            finish()
                        }
                    }
                }
            }
        }
    }

    @OptIn(ExperimentalFoundationApi::class)
    private suspend fun PagerState.scrollToNextPage() {
        animateScrollToPage(currentPage + 1)
    }
}