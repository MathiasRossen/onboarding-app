package dk.mathiasrossen.onboardingapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.createGraph
import dk.mathiasrossen.onboardingapp.navigation.Screen
import dk.mathiasrossen.onboardingapp.ui.appbar.OnboardingBottomAppBar
import dk.mathiasrossen.onboardingapp.ui.appbar.OnboardingTopAppBar
import dk.mathiasrossen.onboardingapp.ui.sources.SourcesScreen
import dk.mathiasrossen.onboardingapp.ui.theme.OnboardingAppTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        installSplashScreen()
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
                            SourcesScreen()
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