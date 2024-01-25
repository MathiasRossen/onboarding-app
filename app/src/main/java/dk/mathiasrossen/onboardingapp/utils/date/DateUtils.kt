package dk.mathiasrossen.onboardingapp.utils.date

import java.time.LocalDate

interface DateUtils {
    fun oneDayPast(): LocalDate

    companion object {
        const val DEFAULT_DISPLAY_PATTERN = "yyyy-MM-dd HH:mm"
        const val DEFAULT_API_PATTERN = "yyyy-MM-dd'T'HH:mm:ss'Z'"
    }
}