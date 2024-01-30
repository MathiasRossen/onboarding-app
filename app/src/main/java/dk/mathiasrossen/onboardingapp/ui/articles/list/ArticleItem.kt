package dk.mathiasrossen.onboardingapp.ui.articles.list

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Divider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import coil.compose.AsyncImage
import dk.mathiasrossen.onboardingapp.R
import dk.mathiasrossen.onboardingapp.data.article.Article
import dk.mathiasrossen.onboardingapp.ui.articles.ArticleItemAuthorRow
import dk.mathiasrossen.onboardingapp.ui.theme.OnboardingAppTheme
import dk.mathiasrossen.onboardingapp.ui.theme.Typography

@Composable
fun ArticleItem(
    article: Article,
    showDivider: Boolean,
    isFavorite: Boolean,
    onFavoriteClick: () -> Unit,
    onClick: () -> Unit
) {
    Column {
        Column(
            modifier = Modifier
                .padding(dimensionResource(id = R.dimen.base_content_padding))
                .clickable { onClick() },
            verticalArrangement = Arrangement.spacedBy(dimensionResource(id = R.dimen.base_arrangement_space_medium))
        ) {
            ArticleItemAuthorRow(authorAndDate = article.authorAndPublishedAt, isFavorite = isFavorite, onFavoriteClick)
            ArticleItemImageAndTitleRow(imageUrl = article.urlToImage, title = article.title)
            Text(
                text = article.description,
                style = Typography.bodyMedium,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis
            )
        }
        if (showDivider) {
            Divider(modifier = Modifier.height(dimensionResource(id = R.dimen.base_divider_size)))
        }
    }
}

@Composable
private fun ArticleItemImageAndTitleRow(imageUrl: String?, title: String) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(dimensionResource(id = R.dimen.base_arrangement_space_medium))
    ) {
        AsyncImage(
            model = imageUrl,
            placeholder = painterResource(id = R.drawable.image_placeholder),
            contentDescription = null,
            modifier = Modifier.width(dimensionResource(id = R.dimen.articles_image_width))
        )
        Text(text = title, style = Typography.titleMedium)
    }
}

@Preview(showBackground = true)
@Composable
private fun ArticleItemPreview() {
    OnboardingAppTheme {
        ArticleItem(
            Article.createSample(),
            showDivider = true,
            isFavorite = false,
            onFavoriteClick = {}
        ) {}
    }
}