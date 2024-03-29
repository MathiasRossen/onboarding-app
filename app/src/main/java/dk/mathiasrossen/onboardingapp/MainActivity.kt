package dk.mathiasrossen.onboardingapp

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.hilt.navigation.compose.hiltViewModel
import dagger.hilt.android.AndroidEntryPoint
import dk.mathiasrossen.onboardingapp.ui.main.MainScreen
import dk.mathiasrossen.onboardingapp.ui.sources.SourcesViewModel
import dk.mathiasrossen.onboardingapp.ui.tutorial.Tutorial
import dk.mathiasrossen.onboardingapp.utils.errors.ErrorActionBus
import dk.mathiasrossen.onboardingapp.utils.errors.ErrorPromoter
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    @Inject
    lateinit var errorPromoter: ErrorPromoter
    @Inject
    lateinit var errorActionBus: ErrorActionBus
    private val sourcesViewModel: SourcesViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        installSplashScreen().setKeepOnScreenCondition {
            sourcesViewModel.isLoading.value
        }
        setContent {
            val mainActivityViewModel: MainActivityViewModel = hiltViewModel()
            if (mainActivityViewModel.isTutorialCompleted) {
                MainScreen(errorPromoter, errorActionBus, sourcesViewModel)
            } else {
                Tutorial {
                    mainActivityViewModel.completeTutorial()
                }
            }
        }
    }
}
