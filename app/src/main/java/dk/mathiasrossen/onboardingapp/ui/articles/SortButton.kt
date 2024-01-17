package dk.mathiasrossen.onboardingapp.ui.articles

import androidx.annotation.StringRes

data class SortButton(@StringRes val titleResource: Int, val sortState: SortState, val onClick: () -> Unit)