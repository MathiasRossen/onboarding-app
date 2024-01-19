package dk.mathiasrossen.onboardingapp.ui.tutorial

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import dk.mathiasrossen.onboardingapp.R
import dk.mathiasrossen.onboardingapp.ui.theme.OnboardingAppTheme
import dk.mathiasrossen.onboardingapp.ui.theme.Typography

@Composable
fun TutorialPageTwo(onButtonClick: () -> Unit) {
    TutorialPage(onButtonClick) {
        Text(
            text = stringResource(id = R.string.tutorial_screen_two_description),
            style = Typography.titleLarge
        )
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
private fun TutorialPageTwoPreview() {
    OnboardingAppTheme {
        TutorialPageTwo { }
    }
}
