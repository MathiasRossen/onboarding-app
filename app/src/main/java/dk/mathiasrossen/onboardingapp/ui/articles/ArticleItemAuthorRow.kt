package dk.mathiasrossen.onboardingapp.ui.articles

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import dk.mathiasrossen.onboardingapp.R
import dk.mathiasrossen.onboardingapp.ui.theme.BlueGray
import dk.mathiasrossen.onboardingapp.ui.theme.Typography

@Composable
fun ArticleItemAuthorRow(authorAndDate: String, favorite: Boolean) {
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