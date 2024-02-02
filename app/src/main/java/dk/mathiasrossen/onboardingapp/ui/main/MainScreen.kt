package dk.mathiasrossen.onboardingapp.ui.main

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navigation
import dk.mathiasrossen.onboardingapp.R
import dk.mathiasrossen.onboardingapp.navigation.Routes
import dk.mathiasrossen.onboardingapp.navigation.Screen
import dk.mathiasrossen.onboardingapp.ui.about.AboutScreen
import dk.mathiasrossen.onboardingapp.ui.appbar.OnboardingBottomAppBar
import dk.mathiasrossen.onboardingapp.ui.appbar.OnboardingLargeTopAppBar
import dk.mathiasrossen.onboardingapp.ui.appbar.OnboardingTopAppBar
import dk.mathiasrossen.onboardingapp.ui.articles.details.ArticleDetailsScreen
import dk.mathiasrossen.onboardingapp.ui.articles.list.ArticlesScreen
import dk.mathiasrossen.onboardingapp.ui.favorites.FavoritesScreen
import dk.mathiasrossen.onboardingapp.ui.sources.SourcesScreen
import dk.mathiasrossen.onboardingapp.ui.sources.SourcesViewModel
import dk.mathiasrossen.onboardingapp.ui.theme.OnboardingAppTheme

@Composable
fun MainScreen(sourcesViewModel: SourcesViewModel) {
    val navController = rememberNavController()
    OnboardingAppTheme {
        val appName = stringResource(R.string.app_name)
        val appBarTitle = remember { mutableStateOf(appName) }
        val appBarImageUrl = remember { mutableStateOf("") }
        Scaffold(
            topBar = {
                val navBackStackEntry by navController.currentBackStackEntryAsState()
                val isBottomBarRoute = listOf(
                    Screen.Sources.route,
                    Screen.Favorites.route,
                    Screen.About.route
                ).contains(navBackStackEntry?.destination?.route)
                val canNavigateBack = navController.previousBackStackEntry !== null && !isBottomBarRoute
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
                        appBarTitle.value = appName
                        SourcesScreen(sourcesViewModel) { sourceId, sourceName ->
                            navController.navigate("${Routes.ARTICLES}/$sourceId?name=$sourceName")
                        }
                    }
                    composable(Screen.Sources.routeArticles) {
                        ArticlesScreen(onAppBarTitle = { title -> appBarTitle.value = title }) { article ->
                            navController.navigate("${Routes.ARTICLE_DETAILS}/${article.uuid}")
                        }
                    }
                    composable(Screen.Sources.routeArticleDetails) {
                        ArticleDetailsScreen(
                            onAppBarTitle = { title -> appBarTitle.value = title },
                            onAppBarImageUrl = { url -> appBarImageUrl.value = url })
                    }
                }
                composable(Screen.Favorites.route) {
                    appBarTitle.value = stringResource(R.string.appbar_title_favorites)
                    FavoritesScreen { article ->
                        navController.navigate("articleDetails/${article.uuid}")
                    }
                }
                composable(Screen.About.route) {
                    appBarTitle.value = stringResource(R.string.appbar_title_about)
                    AboutScreen()
                }
            }
        }
    }
}
