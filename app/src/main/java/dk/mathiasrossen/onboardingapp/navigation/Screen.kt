package dk.mathiasrossen.onboardingapp.navigation

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import dk.mathiasrossen.onboardingapp.R

sealed class Screen(val route: String, @StringRes val titleResourceId: Int, @DrawableRes val iconResourceId: Int) {
    object Sources : Screen("sources", R.string.navigation_sources_title, R.drawable.icon_source_list)
    object Favorites : Screen("favorites", R.string.navigation_favorites_title, R.drawable.icon_favorite)
    object About : Screen("about", R.string.navigation_about_title, R.drawable.icon_about)
}