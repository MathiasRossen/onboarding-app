package dk.mathiasrossen.onboardingapp.ui.appbar

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.LargeTopAppBar
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import coil.compose.AsyncImage
import dk.mathiasrossen.onboardingapp.R
import dk.mathiasrossen.onboardingapp.ui.theme.OnboardingAppTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun OnboardingLargeTopAppBar(
    title: String,
    imageUrl: String?,
    canNavigateBack: Boolean = false,
    onNavigateUp: () -> Unit
) {
    Box(modifier = Modifier.fillMaxWidth()) {
        val placeholderPainter = painterResource(id = R.drawable.image_placeholder)
        AsyncImage(
            modifier = Modifier
                .fillMaxWidth()
                .height(dimensionResource(id = R.dimen.base_large_top_app_bar_height)),
            model = imageUrl,
            placeholder = placeholderPainter,
            error = placeholderPainter,
            contentDescription = null,
            contentScale = ContentScale.Crop
        )
        LargeTopAppBar(
            title = {
                Text(title, maxLines = 2, overflow = TextOverflow.Ellipsis)
            },
            colors = TopAppBarDefaults.topAppBarColors(
                containerColor = Color.Transparent,
                titleContentColor = MaterialTheme.colorScheme.onPrimaryContainer
            ),
            navigationIcon = { TopAppBarBackButton(canNavigateBack, onNavigateUp) }
        )
    }
}

@Preview
@Composable
private fun TopAppBarPreview() {
    OnboardingAppTheme {
        OnboardingLargeTopAppBar("Someone was doing something somewhere and it was quite a sight", null) {}
    }
}