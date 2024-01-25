package dk.mathiasrossen.onboardingapp.data.room_type_converters

import androidx.room.ProvidedTypeConverter
import androidx.room.TypeConverter
import java.time.Instant
import java.time.LocalDateTime
import java.time.ZoneId

@ProvidedTypeConverter
class LocalDateTimeConverter {
    private val zoneId = ZoneId.systemDefault()

    @TypeConverter
    fun localDateTimeToString(localDateTime: LocalDateTime): Long = localDateTime.atZone(zoneId).toEpochSecond()

    @TypeConverter
    fun stringToLocalDateTime(epochSeconds: Long): LocalDateTime =
        Instant.ofEpochSecond(epochSeconds).atZone(zoneId).toLocalDateTime()
}