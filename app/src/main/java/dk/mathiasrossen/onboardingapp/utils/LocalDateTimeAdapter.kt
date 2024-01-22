package dk.mathiasrossen.onboardingapp.utils

import com.squareup.moshi.FromJson
import com.squareup.moshi.ToJson
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class LocalDateTimeAdapter {
    @FromJson
    fun fromJson(dateTimeString: String): LocalDateTime =
        LocalDateTime.parse(dateTimeString, DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss'Z'"))

    // We are never converting to json
    @ToJson
    fun toJson(localDateTime: LocalDateTime) = ""
}