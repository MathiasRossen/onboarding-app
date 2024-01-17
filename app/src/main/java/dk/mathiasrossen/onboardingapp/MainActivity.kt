package dk.mathiasrossen.onboardingapp

import android.content.Intent
import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.ui.Modifier
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import androidx.navigation.compose.rememberNavController
import dagger.hilt.android.AndroidEntryPoint
import dk.mathiasrossen.onboardingapp.navigation.Screen
import dk.mathiasrossen.onboardingapp.ui.appbar.OnboardingBottomAppBar
import dk.mathiasrossen.onboardingapp.ui.appbar.OnboardingTopAppBar
import dk.mathiasrossen.onboardingapp.ui.articles.list.ArticleList
import dk.mathiasrossen.onboardingapp.ui.articles.list.ArticleListViewModel
import dk.mathiasrossen.onboardingapp.ui.sources.SourcesScreen
import dk.mathiasrossen.onboardingapp.ui.sources.SourcesScreenViewModel
import dk.mathiasrossen.onboardingapp.ui.theme.OnboardingAppTheme
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val sourcesScreenViewModel: SourcesScreenViewModel by viewModels()
        val isTutorialCompleted: Boolean
        runBlocking(Dispatchers.IO) {
            isTutorialCompleted = TutorialActivity.isTutorialCompleted(this@MainActivity).first()
        }
        if (!isTutorialCompleted) {
            startActivity(Intent(this, TutorialActivity::class.java))
        }
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
                        modifier = Modifier.padding(innerPadding),
                    ) {
                        navigation(startDestination = Screen.Sources.routeMain, route = Screen.Sources.route) {
                            composable(Screen.Sources.routeMain) {
                                SourcesScreen(sourcesScreenViewModel) { articleId ->
                                    navController.navigate("articles/${articleId}")
                                }
                            }
                            composable(Screen.Sources.routeArticles) {
                                val articleListViewModel = hiltViewModel<ArticleListViewModel>()
                                ArticleList(articleListViewModel)
                            }
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