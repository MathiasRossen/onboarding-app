package dk.mathiasrossen.onboardingapp.ui.articles.list

import androidx.annotation.StringRes
import dk.mathiasrossen.onboardingapp.R

enum class SortState(@StringRes val title: Int) {
    POPULAR_TODAY(R.string.articles_screen_button_sort_popular_today),
    POPULAR_ALL_TIME(R.string.articles_screen_button_sort_popular_all_time),
    NEWEST(R.string.articles_screen_button_sort_newest)
}