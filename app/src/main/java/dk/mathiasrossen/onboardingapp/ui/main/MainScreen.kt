package dk.mathiasrossen.onboardingapp.ui.main

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navigation
import dk.mathiasrossen.onboardingapp.navigation.Screen
import dk.mathiasrossen.onboardingapp.ui.appbar.OnboardingBottomAppBar
import dk.mathiasrossen.onboardingapp.ui.appbar.OnboardingTopAppBar
import dk.mathiasrossen.onboardingapp.ui.articles.ArticleList
import dk.mathiasrossen.onboardingapp.ui.articles.ArticleListViewModel
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