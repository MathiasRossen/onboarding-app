package dk.mathiasrossen.onboardingapp.api.adapters

import com.squareup.moshi.FromJson
import com.squareup.moshi.ToJson
import dk.mathiasrossen.onboardingapp.utils.date.DateUtils
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class LocalDateTimeAdapter {
    @FromJson
    fun fromJson(dateTimeString: String): LocalDateTime =
        LocalDateTime.parse(dateTimeString, DateTimeFormatter.ofPattern(DateUtils.DEFAULT_API_PATTERN))

    // We are never converting to json
    @ToJson
    fun toJson(localDateTime: LocalDateTime) = ""
}