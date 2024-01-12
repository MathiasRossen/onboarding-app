package dk.mathiasrossen.onboardingapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.ui.Modifier
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import dk.mathiasrossen.onboardingapp.navigation.Screen
import dk.mathiasrossen.onboardingapp.ui.appbar.OnboardingBottomAppBar
import dk.mathiasrossen.onboardingapp.ui.appbar.OnboardingTopAppBar
import dk.mathiasrossen.onboardingapp.ui.sources.SourcesScreen
import dk.mathiasrossen.onboardingapp.ui.sources.SourcesScreenViewModel
import dk.mathiasrossen.onboardingapp.ui.theme.OnboardingAppTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val sourcesScreenViewModel by viewModels<SourcesScreenViewModel>()
        installSplashScreen().setKeepOnScreenCondition {
            sourcesScreenViewModel.newsSources.value.isEmpty()
        }
        setContent {
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
    }
}