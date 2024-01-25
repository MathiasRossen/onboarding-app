package dk.mathiasrossen.onboardingapp.ui.articles.list

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Divider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import dk.mathiasrossen.onboardingapp.R
import dk.mathiasrossen.onboardingapp.models.Article
import dk.mathiasrossen.onboardingapp.ui.theme.BlueGray
import dk.mathiasrossen.onboardingapp.ui.theme.OnboardingAppTheme
import dk.mathiasrossen.onboardingapp.ui.theme.Typography

@Composable
fun ArticleItem(article: Article, showDivider: Boolean) {
    Column {
        Column(
            modifier = Modifier.padding(dimensionResource(id = R.dimen.base_content_padding)),
            verticalArrangement = Arrangement.spacedBy(dimensionResource(id = R.dimen.base_arrangement_space_medium))
        ) {
            ArticleItemAuthorRow(authorAndDate = article.authorAndPublishedAt, favorite = false)
            ArticleItemImageAndTitleRow(imageUrl = article.urlToImage, title = article.title)
            if (article.description != null) {
                Text(
                    text = article.description,
                    style = Typography.bodyMedium,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis
                )
            }
        }
        if (showDivider) {
            Divider(modifier = Modifier.height(dimensionResource(id = R.dimen.base_divider_size)))
        }
    }
}

@Composable
private fun ArticleItemAuthorRow(authorAndDate: String, favorite: Boolean) {
    val favoriteResource = if (favorite) R.drawable.icon_favorite_active else R.drawable.icon_favorite
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(text = authorAndDate, style = Typography.labelLarge, color = BlueGray)
        Image(
            modifier = Modifier.size(24.dp),
            painter = painterResource(id = favoriteResource),
            contentDescription = null,
            colorFilter = ColorFilter.tint(
                BlueGray
            )
        )
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
            true
        )
    }
}