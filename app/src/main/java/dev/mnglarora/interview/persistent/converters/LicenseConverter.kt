package dev.mnglarora.interview.persistent.converters

import androidx.room.TypeConverter
import dev.mnglarora.interview.model.License
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json



class LicenseConverter {

    private val json = Json { ignoreUnknownKeys = true;isLenient = true }

    @TypeConverter
    fun fromLicense(value: License?): String? {
        return value?.let { json.encodeToString(it) }
    }

    @TypeConverter
    fun stringToLicense(value: String?): License? {
        return value?.let { json.decodeFromString<License>(it) }
    }

}