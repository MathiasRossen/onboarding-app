package dk.mathiasrossen.onboardingapp.ui.tutorial

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import dk.mathiasrossen.onboardingapp.R
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
                .padding(dimensionResource(id = R.dimen.tutorial_page_image_padding))
        )
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
private fun TutorialPageOnePreview() {
    OnboardingAppTheme {
        TutorialPageOne { }
    }
}
