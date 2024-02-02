package dk.mathiasrossen.onboardingapp.ui.articles

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import dk.mathiasrossen.onboardingapp.R
import dk.mathiasrossen.onboardingapp.ui.theme.BlueGray
import dk.mathiasrossen.onboardingapp.ui.theme.OnboardingAppTheme
import dk.mathiasrossen.onboardingapp.ui.theme.Typography

@Composable
fun ArticleItemAuthorRow(authorAndDate: String, isFavorite: Boolean, onFavoriteClick: () -> Unit) {
    val favoriteResource = if (isFavorite) R.drawable.icon_favorite_active else R.drawable.icon_favorite
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(text = authorAndDate, style = Typography.labelLarge, color = BlueGray)
        IconButton(
            onClick = onFavoriteClick,
            modifier = Modifier.size(dimensionResource(id = R.dimen.base_icon_size))
        ) {
            Icon(painter = painterResource(id = favoriteResource), contentDescription = null, tint = BlueGray)
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun ArticleItemAuthorRowPreview() {
    OnboardingAppTheme {
        ArticleItemAuthorRow(authorAndDate = "Hest Hestesen - 1010/10/10 10:10", isFavorite = false) {}
    }
}