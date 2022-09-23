package dev.mnglarora.interview.di

import androidx.room.Room
import dev.mnglarora.interview.R
import dev.mnglarora.interview.persistent.AppDatabase
import org.koin.android.ext.koin.androidApplication
import org.koin.dsl.module

val persistentModule = module {

    single {
        Room.databaseBuilder(
            androidApplication(),
            AppDatabase::class.java,
            androidApplication().getString(R.string.database)
        ).fallbackToDestructiveMigration().build()
    }

    single { get<AppDatabase>().githubRepoDao() }

}