package dk.mathiasrossen.onboardingapp.ui.tutorial

import androidx.annotation.StringRes
import androidx.compose.foundation.Image
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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import dk.mathiasrossen.onboardingapp.R
import dk.mathiasrossen.onboardingapp.ui.theme.ButtonColors
import dk.mathiasrossen.onboardingapp.ui.theme.OnboardingAppTheme
import dk.mathiasrossen.onboardingapp.ui.theme.Typography

@Composable
fun TutorialPageOne(onButtonClick: () -> Unit) {
    TutorialPage(onButtonClick) {
        Text(
            text = stringResource(id = R.string.tutorial_screen_one_header),
            textAlign = TextAlign.Center,
            modifier = Modifier.fillMaxWidth(),
            style = Typography.headlineLarge
        )
        Text(
            text = stringResource(id = R.string.tutorial_screen_one_subheader),
            textAlign = TextAlign.Center,
            modifier = Modifier.fillMaxWidth(),
            style = Typography.titleLarge
        )
        Image(
            painter = painterResource(id = R.drawable.kitten),
            contentDescription = null,
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        )
    }
}

@Composable
fun TutorialPageTwo(onButtonClick: () -> Unit) {
    TutorialPage(onButtonClick) {
        Text(
            text = stringResource(id = R.string.tutorial_screen_two_description),
            style = Typography.titleLarge
        )
    }
}

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
private fun TutorialPage(
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
private fun TutorialPageOnePreview() {
    OnboardingAppTheme {
        TutorialPageOne { }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
private fun TutorialPageTwoPreview() {
    OnboardingAppTheme {
        TutorialPageTwo { }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
private fun TutorialPageThreePreview() {
    OnboardingAppTheme {
        TutorialPageThree { }
    }
}