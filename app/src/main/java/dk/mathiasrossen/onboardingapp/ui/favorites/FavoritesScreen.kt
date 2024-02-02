package dk.mathiasrossen.onboardingapp.ui.favorites

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import dk.mathiasrossen.onboardingapp.data.article.Article
import dk.mathiasrossen.onboardingapp.ui.articles.list.ArticleItem
import dk.mathiasrossen.onboardingapp.ui.theme.OnboardingAppTheme

@Composable
fun FavoritesScreen(
    favoritesViewModel: FavoritesViewModel = hiltViewModel(),
    onArticleClick: (article: Article) -> Unit
) {
    favoritesViewModel.loadArticles()
    val articles by remember { favoritesViewModel.articles }
    if (articles.isEmpty()) {
        NoFavoriteArticlesPlaceholder()
    } else {
        FavoriteArticlesList(
            articles = favoritesViewModel.articles.value,
            onFavoriteClick = { article -> favoritesViewModel.toggleFavorite(article) },
            onArticleClick = onArticleClick
        )
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
private fun FavoriteArticlesList(
    articles: List<Article>,
    onFavoriteClick: (article: Article) -> Unit,
    onArticleClick: (article: Article) -> Unit
) {
    LazyColumn {
        itemsIndexed(articles, key = { _, article -> article.uuid }) { index, article ->
            Row(
                modifier = Modifier.animateItemPlacement()
            ) {
                ArticleItem(
                    article = article,
                    showDivider = index != articles.lastIndex,
                    isFavorite = true,
                    onFavoriteClick = { onFavoriteClick(article) },
                    onClick = { onArticleClick(article) }
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun FavoriteArticlesListPreview() {
    OnboardingAppTheme {
        FavoriteArticlesList(listOf(Article.createSample(), Article.createSample()), {}, {})
    }
}