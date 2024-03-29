package dk.mathiasrossen.onboardingapp.ui.appbar

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

@Composable
fun TopAppBarBackButton(canNavigateBack: Boolean, onNavigateUp: () -> Unit) {
    if (canNavigateBack) {
        IconButton(onClick = onNavigateUp) {
            Icon(
                imageVector = Icons.Filled.ArrowBack,
                contentDescription = null,
                tint = Color.White
            )
        }
    }
}