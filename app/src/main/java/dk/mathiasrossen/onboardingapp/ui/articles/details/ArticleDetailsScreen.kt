package dk.mathiasrossen.onboardingapp.ui.articles.details

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import dk.mathiasrossen.onboardingapp.R
import dk.mathiasrossen.onboardingapp.data.article.Article
import dk.mathiasrossen.onboardingapp.ui.articles.ArticleItemAuthorRow
import dk.mathiasrossen.onboardingapp.ui.theme.OnboardingAppTheme
import dk.mathiasrossen.onboardingapp.ui.theme.Typography

@Composable
fun ArticleDetailsScreen(
    articleDetailsViewModel: ArticleDetailsViewModel = hiltViewModel(),
    onAppBarTitle: (title: String) -> Unit,
    onAppBarImageUrl: (url: String) -> Unit
) {
    val article = articleDetailsViewModel.articleState.value
    if (article != null) {
        onAppBarTitle(article.title)
        onAppBarImageUrl(article.urlToImage)
        ArticleDetails(article, articleDetailsViewModel.favoriteState.value) {
            articleDetailsViewModel.toggleFavorite()
        }
    }
}

@Composable
private fun ArticleDetails(article: Article, isFavorite: Boolean, onFavoriteClick: () -> Unit) {
    Column(
        modifier = Modifier.padding(dimensionResource(id = R.dimen.base_content_padding)),
        verticalArrangement = Arrangement.spacedBy(
            dimensionResource(id = R.dimen.base_arrangement_space_large)
        )
    ) {
        ArticleItemAuthorRow(article.authorAndPublishedAt, isFavorite, onFavoriteClick)
        Text(text = article.title, style = Typography.titleLarge)
        Text(text = article.description)
    }
}

@Preview(showBackground = true)
@Composable
fun ArticleDetailsPreview() {
    OnboardingAppTheme {
        ArticleDetails(Article.createSample(), false) {}
    }
}
