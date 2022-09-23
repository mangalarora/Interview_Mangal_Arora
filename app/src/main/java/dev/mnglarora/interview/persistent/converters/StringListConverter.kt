package dev.mnglarora.interview.persistent.converters

import androidx.room.TypeConverter
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

class StringListConverter {

    @TypeConverter
    fun fromList(value: List<String>?): String? {
        return value?.let { Json.encodeToString(it) }
    }

    @TypeConverter
    fun  dataToList(value: String?): List<String>? {
        return value?.let { Json.decodeFromString<List<String>>(it) }
    }

}