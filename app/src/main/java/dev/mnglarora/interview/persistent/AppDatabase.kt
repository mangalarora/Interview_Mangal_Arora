package dev.mnglarora.interview.persistent

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import dev.mnglarora.interview.model.GhRepo
import dev.mnglarora.interview.persistent.converters.LicenseConverter
import dev.mnglarora.interview.persistent.converters.OwnerConverter
import dev.mnglarora.interview.persistent.converters.StringListConverter

@Database(entities = [GhRepo::class], version = 1, exportSchema = false)
@TypeConverters(LicenseConverter::class, OwnerConverter::class, StringListConverter::class)
abstract class AppDatabase : RoomDatabase() {

    abstract fun githubRepoDao(): GhRepoDao

}