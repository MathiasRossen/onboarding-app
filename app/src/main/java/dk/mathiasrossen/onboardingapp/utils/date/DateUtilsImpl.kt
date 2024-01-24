package dk.mathiasrossen.onboardingapp.utils.date

import java.time.LocalDate

class DateUtilsImpl : DateUtils {
    override fun oneDayPast(): LocalDate = LocalDate.now().minusDays(1)
}