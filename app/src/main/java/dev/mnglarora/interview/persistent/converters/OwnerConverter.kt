package dev.mnglarora.interview.persistent.converters

import androidx.room.TypeConverter
import dev.mnglarora.interview.model.Owner
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

class OwnerConverter {


    @TypeConverter
    fun fromOwner(value: Owner?): String? {
        return value?.let { Json.encodeToString(it) }
    }

    @TypeConverter
    fun stringToOwner(value: String?): Owner? {
        return value?.let { Json.decodeFromString<Owner>(it) }
    }


}