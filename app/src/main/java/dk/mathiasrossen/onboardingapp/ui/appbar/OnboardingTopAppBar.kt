package dk.mathiasrossen.onboardingapp.ui.appbar

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import dk.mathiasrossen.onboardingapp.ui.theme.OnboardingAppTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun OnboardingTopAppBar(title: String, canNavigateBack: Boolean = false, onNavigateUp: () -> Unit) {
    TopAppBar(
        title = {
            Text(title)
        },
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = MaterialTheme.colorScheme.primaryContainer,
            titleContentColor = MaterialTheme.colorScheme.onPrimaryContainer
        ),
        navigationIcon = { TopAppBarBackButton(canNavigateBack, onNavigateUp) }
    )
}

@Preview
@Composable
private fun TopAppBarPreview() {
    OnboardingAppTheme {
        OnboardingTopAppBar("NewsApp") {}
    }
}