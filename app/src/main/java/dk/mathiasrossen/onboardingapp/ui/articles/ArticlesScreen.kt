package dk.mathiasrossen.onboardingapp.ui.articles

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.pullrefresh.pullRefresh
import androidx.compose.material.pullrefresh.rememberPullRefreshState
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import dk.mathiasrossen.onboardingapp.R
import dk.mathiasrossen.onboardingapp.models.Article
import dk.mathiasrossen.onboardingapp.ui.theme.ButtonColors
import dk.mathiasrossen.onboardingapp.ui.theme.OnboardingAppTheme

@Composable
fun ArticleList(articleListViewModel: ArticleListViewModel = hiltViewModel()) {
    ArticleList(
        articleListViewModel.articles.value,
        articleListViewModel.sortState.value,
        articleListViewModel.isRefreshing.value,
        articleListViewModel::refresh,
        articleListViewModel::sortByPopularToday,
        articleListViewModel::sortByPopularAllTime,
        articleListViewModel::sortByNewest
    )
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun ArticleList(
    articles: List<Article>,
    currentSortState: SortState,
    isRefreshing: Boolean,
    onRefresh: () -> Unit,
    onPopularTodayClick: () -> Unit,
    onPopularAllTimeClick: () -> Unit,
    onNewestClick: () -> Unit
) {
    val pullRefreshState =
        rememberPullRefreshState(refreshing = isRefreshing, onRefresh = onRefresh, refreshThreshold = 120.dp)
    LazyColumn(modifier = Modifier.pullRefresh(pullRefreshState)) {
        item {
            val rowContentPadding = dimensionResource(id = R.dimen.base_content_padding)
            LazyRow(
                contentPadding = PaddingValues(
                    start = rowContentPadding,
                    top = rowContentPadding,
                    end = rowContentPadding
                ),
                horizontalArrangement = Arrangement.spacedBy(
                    dimensionResource(id = R.dimen.base_arrangement_space_medium)
                )
            ) {
                items(SortState.entries) { sortState ->
                    val colors =
                        if (sortState == currentSortState) {
                            ButtonColors.defaultFilled()
                        } else {
                            ButtonColors.defaultTonal()
                        }
                    val onButtonClick = when (sortState) {
                        SortState.POPULAR_TODAY -> onPopularTodayClick
                        SortState.POPULAR_ALL_TIME -> onPopularAllTimeClick
                        else -> onNewestClick
                    }
                    Button(onClick = onButtonClick, colors = colors) {
                        Text(stringResource(id = sortState.title))
                    }
                }
            }
        }
        itemsIndexed(articles) { index, article ->
            ArticleItem(article = article, showDivider = index != articles.lastIndex)
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun ArticleListPreview() {
    OnboardingAppTheme {
        ArticleList(
            listOf(Article.createSample(), Article.createSample(), Article.createSample()),
            SortState.POPULAR_TODAY,
            true,
            {},
            {},
            {},
            {}
        )
    }
}