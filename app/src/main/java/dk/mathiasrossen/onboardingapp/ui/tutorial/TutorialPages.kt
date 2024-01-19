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
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import dk.mathiasrossen.onboardingapp.R
import dk.mathiasrossen.onboardingapp.ui.theme.ButtonColors

@Composable
fun TutorialPage(
    onButtonClick: () -> Unit,
    @StringRes buttonTextResource: Int = R.string.tutorial_button_next,
    content: @Composable () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(dimensionResource(id = R.dimen.tutorial_page_content_padding))
    ) {
        Column(
            modifier = Modifier.weight(1f),
            verticalArrangement = Arrangement.spacedBy(
                space = dimensionResource(id = R.dimen.base_arrangement_space_small), Alignment.CenterVertically
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
