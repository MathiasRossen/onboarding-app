package dk.mathiasrossen.onboardingapp.ui.main

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navigation
import dk.mathiasrossen.onboardingapp.navigation.Screen
import dk.mathiasrossen.onboardingapp.ui.appbar.OnboardingBottomAppBar
import dk.mathiasrossen.onboardingapp.ui.appbar.OnboardingLargeTopAppBar
import dk.mathiasrossen.onboardingapp.ui.appbar.OnboardingTopAppBar
import dk.mathiasrossen.onboardingapp.ui.articles.details.ArticleDetailsScreen
import dk.mathiasrossen.onboardingapp.ui.articles.list.ArticlesScreen
import dk.mathiasrossen.onboardingapp.ui.sources.SourcesScreen
import dk.mathiasrossen.onboardingapp.ui.sources.SourcesScreenViewModel
import dk.mathiasrossen.onboardingapp.ui.theme.OnboardingAppTheme

@Composable
fun MainScreen(sourcesScreenViewModel: SourcesScreenViewModel) {
    val navController = rememberNavController()
    OnboardingAppTheme {
        val appBarTitle = remember { mutableStateOf("NewsApp") }
        val appBarImageUrl = remember { mutableStateOf<String?>(null) }
        Scaffold(
            topBar = {
                val navBackStackEntry by navController.currentBackStackEntryAsState()
                val canNavigateBack = navController.previousBackStackEntry !== null
                if (navBackStackEntry?.destination?.route == Screen.Sources.routeArticleDetails) {
                    OnboardingLargeTopAppBar(
                        appBarTitle.value,
                        appBarImageUrl.value,
                        canNavigateBack,
                        navController::navigateUp
                    )
                } else {
                    OnboardingTopAppBar(
                        appBarTitle.value,
                        canNavigateBack,
                        navController::navigateUp
                    )
                }
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
                        appBarTitle.value = "NewsApp"
                        SourcesScreen(sourcesScreenViewModel) { sourceId, sourceName ->
                            navController.navigate("articles/$sourceId?name=$sourceName")
                        }
                    }
                    composable(Screen.Sources.routeArticles) {
                        ArticlesScreen(onAppBarTitle = { title -> appBarTitle.value = title }) { article ->
                            navController.navigate("articleDetails/${article.uuid}")
                        }
                    }
                    composable(Screen.Sources.routeArticleDetails) {
                        ArticleDetailsScreen(
                            onAppBarTitle = { title -> appBarTitle.value = title },
                            onAppBarImageUrl = { url -> appBarImageUrl.value = url })
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