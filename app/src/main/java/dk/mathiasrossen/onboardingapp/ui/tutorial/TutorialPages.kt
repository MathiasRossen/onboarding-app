package dk.mathiasrossen.onboardingapp.ui.tutorial

import androidx.annotation.StringRes
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import dk.mathiasrossen.onboardingapp.R
import dk.mathiasrossen.onboardingapp.ui.theme.ButtonColors
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

@Composable
fun TutorialPage(
    onButtonClick: () -> Unit,
    @StringRes buttonTextResource: Int = R.string.tutorial_button_next,
    content: @Composable () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(36.dp)
    ) {
        Column(
            modifier = Modifier.weight(1f),
            verticalArrangement = Arrangement.spacedBy(
                space = 8.dp, Alignment.CenterVertically
            )
        ) {
            content()
        }
        Button(
            onClick = { onButtonClick() },
            modifier = Modifier.fillMaxWidth(),
            colors = ButtonColors.defaultFilled()
        ) {
            Text(text = stringResource(buttonTextResource))
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
private fun TutorialPageThreePreview() {
    OnboardingAppTheme {
        TutorialPageThree { }
    }
}