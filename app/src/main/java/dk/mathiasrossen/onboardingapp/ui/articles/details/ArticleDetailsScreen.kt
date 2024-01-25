package dk.mathiasrossen.onboardingapp.ui.articles.details

import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import dk.mathiasrossen.onboardingapp.ui.theme.OnboardingAppTheme

@Composable
fun ArticleDetailsScreen(articleDetailsViewModel: ArticleDetailsViewModel = hiltViewModel()) {
}

@Preview(showBackground = true)
@Composable
fun ArticleDetailsPreview() {
    OnboardingAppTheme {
        ArticleDetailsScreen()
    }
}
