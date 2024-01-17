package dk.mathiasrossen.onboardingapp.utils

import com.squareup.moshi.FromJson
import com.squareup.moshi.ToJson
import org.joda.time.LocalDateTime
import org.joda.time.format.DateTimeFormat

class LocalDateTimeAdapter {
    @FromJson
    fun fromJson(dateTimeString: String): LocalDateTime {
        val formatter = DateTimeFormat.forPattern("yyyy-MM-dd'T'HH:mm:ss'Z'")
        return formatter.parseLocalDateTime(dateTimeString)
    }

    // We are never converting to json
    @ToJson
    fun toJson(localDateTime: LocalDateTime) = ""
}