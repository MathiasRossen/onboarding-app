package dk.mathiasrossen.onboardingapp.ui.about

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import dk.mathiasrossen.onboardingapp.BuildConfig
import dk.mathiasrossen.onboardingapp.R
import dk.mathiasrossen.onboardingapp.ui.theme.OnboardingAppTheme
import dk.mathiasrossen.onboardingapp.ui.theme.Typography

@Composable
fun AboutScreen() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(dimensionResource(id = R.dimen.base_content_padding)),
        verticalArrangement = Arrangement.spacedBy(
            dimensionResource(id = R.dimen.base_arrangement_space_medium)
        )
    ) {
        Text(text = stringResource(R.string.about_screen_header), style = Typography.titleLarge)
        Text(text = stringResource(R.string.about_screen_body1))
        Text(text = stringResource(R.string.about_screen_body2))
        Spacer(modifier = Modifier.weight(1f))
        Text(text = stringResource(R.string.about_version, BuildConfig.VERSION_NAME), style = Typography.titleLarge)
    }
}

@Preview(showBackground = true)
@Composable
private fun AboutScreenPreview() {
    OnboardingAppTheme {
        AboutScreen()
    }
}
