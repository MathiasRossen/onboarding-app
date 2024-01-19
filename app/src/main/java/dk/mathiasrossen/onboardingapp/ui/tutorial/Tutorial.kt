package dk.mathiasrossen.onboardingapp.ui.tutorial

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import kotlinx.coroutines.launch

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun Tutorial(onTutorialCompleted: () -> Unit) {
    val pages = 3
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
            2 -> TutorialPageThree(onTutorialCompleted)
        }
    }
}

@OptIn(ExperimentalFoundationApi::class)
private suspend fun PagerState.scrollToNextPage() {
    animateScrollToPage(currentPage + 1)
}
