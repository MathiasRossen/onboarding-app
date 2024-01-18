package dk.mathiasrossen.onboardingapp

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.hilt.navigation.compose.hiltViewModel
import dagger.hilt.android.AndroidEntryPoint
import dk.mathiasrossen.onboardingapp.ui.main.MainScreen
import dk.mathiasrossen.onboardingapp.ui.sources.SourcesScreenViewModel
import dk.mathiasrossen.onboardingapp.ui.tutorial.Tutorial

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private val sourcesScreenViewModel: SourcesScreenViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        installSplashScreen().setKeepOnScreenCondition {
            sourcesScreenViewModel.newsSources.value.isEmpty()
        }
        setContent {
            val mainActivityViewModel: MainActivityViewModel = hiltViewModel()
            if (mainActivityViewModel.isTutorialCompleted) {
                MainScreen(sourcesScreenViewModel)
            } else {
                Tutorial {
                    mainActivityViewModel.completeTutorial()
                }
            }
        }
    }
}