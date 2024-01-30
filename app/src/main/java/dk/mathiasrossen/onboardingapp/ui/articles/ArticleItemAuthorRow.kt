package dk.mathiasrossen.onboardingapp.ui.articles

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import dk.mathiasrossen.onboardingapp.R
import dk.mathiasrossen.onboardingapp.ui.theme.BlueGray
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
        Image(
            modifier = Modifier
                .size(dimensionResource(id = R.dimen.base_icon_size))
                .clickable(onClick = onFavoriteClick, interactionSource = remember {
                    MutableInteractionSource()
                }, indication = null),
            painter = painterResource(id = favoriteResource),
            contentDescription = null,
            colorFilter = ColorFilter.tint(
                BlueGray
            )
        )
    }
}