package dk.mathiasrossen.onboardingapp.ui.main

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import dk.mathiasrossen.onboardingapp.navigation.Screen
import dk.mathiasrossen.onboardingapp.ui.appbar.OnboardingBottomAppBar
import dk.mathiasrossen.onboardingapp.ui.appbar.OnboardingTopAppBar
import dk.mathiasrossen.onboardingapp.ui.sources.SourcesScreen
import dk.mathiasrossen.onboardingapp.ui.sources.SourcesScreenViewModel
import dk.mathiasrossen.onboardingapp.ui.theme.OnboardingAppTheme

@Composable
fun MainScreen(sourcesScreenViewModel: SourcesScreenViewModel) {
    val navController = rememberNavController()
    OnboardingAppTheme {
        Scaffold(
            topBar = {
                OnboardingTopAppBar()
            },
            bottomBar = {
                OnboardingBottomAppBar(navController)
            }
        ) { innerPadding ->
            NavHost(
                navController = navController,
                startDestination = Screen.Sources.route,
                modifier = Modifier.padding(innerPadding)
            ) {
                composable(Screen.Sources.route) {
                    SourcesScreen(sourcesScreenViewModel)
                }
                composable(Screen.Favorites.route) {
                    Text(text = "Favorites")
                }
                composable(Screen.About.route) {
                    Text(text = "About")
                }
            }
        }
    }
}