package dk.mathiasrossen.onboardingapp.ui.articles.list

import android.annotation.SuppressLint
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
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import dk.mathiasrossen.onboardingapp.R
import dk.mathiasrossen.onboardingapp.data.article.Article
import dk.mathiasrossen.onboardingapp.ui.theme.ButtonColors
import dk.mathiasrossen.onboardingapp.ui.theme.OnboardingAppTheme

@Composable
fun ArticlesScreen(
    articlesViewModel: ArticlesViewModel = hiltViewModel(),
    onAppBarTitle: (title: String) -> Unit,
    onArticleClick: (article: Article) -> Unit
) {
    onAppBarTitle(articlesViewModel.appBarTitle.value)
    ArticleList(
        articlesViewModel.articles,
        articlesViewModel.sortState.value,
        articlesViewModel.isRefreshing.value,
        articlesViewModel::refresh,
        articlesViewModel::setSortState,
        articlesViewModel::toggleFavorite,
        onArticleClick
    )
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
private fun ArticleList(
    articles: Map<Article, State<Boolean>>,
    currentSortState: SortState,
    isRefreshing: Boolean,
    onRefresh: () -> Unit,
    onSortOptionClick: (sortState: SortState) -> Unit,
    onFavoriteClick: (article: Article) -> Unit,
    onArticleClick: (article: Article) -> Unit
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
                    val colors = if (sortState == currentSortState) {
                        ButtonColors.defaultFilled()
                    } else {
                        ButtonColors.defaultTonal()
                    }
                    Button(onClick = { onSortOptionClick(sortState) }, colors = colors) {
                        Text(stringResource(id = sortState.title))
                    }
                }
            }
        }
        val articlesList = articles.keys.toList()
        itemsIndexed(articlesList) { index, article ->
            ArticleItem(
                article = article,
                showDivider = index != articlesList.lastIndex,
                isFavorite = articles[article]?.value == true,
                { onFavoriteClick(article) }) {
                onArticleClick(article)
            }
        }
    }
}

@SuppressLint("UnrememberedMutableState")
@Preview(showBackground = true)
@Composable
private fun ArticleListPreview() {
    OnboardingAppTheme {
        ArticleList(
            mapOf(
                Article.createSample() to mutableStateOf(false),
                Article.createSample() to mutableStateOf(false)
            ),
            SortState.POPULAR_TODAY,
            true,
            {},
            {},
            {},
            {}
        )
    }
}