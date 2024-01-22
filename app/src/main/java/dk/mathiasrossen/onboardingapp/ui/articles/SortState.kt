package dk.mathiasrossen.onboardingapp.ui.articles

import androidx.annotation.StringRes
import dk.mathiasrossen.onboardingapp.R

enum class SortState(@StringRes val title: Int) {
    POPULAR_TODAY(R.string.article_list_button_sort_popular_today),
    POPULAR_ALL_TIME(R.string.article_list_button_sort_popular_all_time),
    NEWEST(R.string.article_list_button_sort_newest)
}