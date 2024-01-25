package dk.mathiasrossen.onboardingapp.ui.theme

import androidx.compose.material3.ButtonDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

object ButtonColors {
    @Composable
    fun defaultFilled() = ButtonDefaults.buttonColors(
        containerColor = BlueGray,
        contentColor = Color.White
    )

    @Composable
    fun defaultTonal() = ButtonDefaults.buttonColors(
        containerColor = GrayLight,
        contentColor = Gray
    )
}