package dk.mathiasrossen.onboardingapp.navigation

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import dk.mathiasrossen.onboardingapp.R

sealed class Screen(val route: String, @StringRes val titleResourceId: Int, @DrawableRes val iconResourceId: Int) {
    data object Sources : Screen(Routes.SOURCES, R.string.navigation_sources_title, R.drawable.icon_source_list) {
        const val routeMain = Routes.MAIN
        const val routeArticles = "${Routes.ARTICLES}/{sourceId}?name={sourceName}"
        const val routeArticleDetails = "${Routes.ARTICLE_DETAILS}/{articleUuid}"
    }
    data object Favorites : Screen(Routes.FAVORITES, R.string.navigation_favorites_title, R.drawable.icon_favorite)
    data object About : Screen(Routes.ABOUT, R.string.navigation_about_title, R.drawable.icon_about)
}