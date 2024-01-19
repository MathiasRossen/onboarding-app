package dk.mathiasrossen.onboardingapp.ui.tutorial

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import dk.mathiasrossen.onboardingapp.R
import dk.mathiasrossen.onboardingapp.ui.theme.OnboardingAppTheme
import dk.mathiasrossen.onboardingapp.ui.theme.Typography

@Composable
fun TutorialPageThree(onButtonClick: () -> Unit) {
    TutorialPage(onButtonClick, buttonTextResource = R.string.tutorial_screen_three_button) {
        Text(
            text = stringResource(id = R.string.tutorial_screen_three_description),
            style = Typography.titleLarge
        )
        Text(text = stringResource(R.string.tutorial_screen_three_motivational_text), style = Typography.headlineLarge)
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
private fun TutorialPageThreePreview() {
    OnboardingAppTheme {
        TutorialPageThree { }
    }
}
