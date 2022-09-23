package dev.mnglarora.interview.di

import dev.mnglarora.interview.sync.worker.SyncWorker
import org.koin.android.ext.koin.androidApplication
import org.koin.androidx.workmanager.dsl.worker
import org.koin.dsl.module

val workerModule = module {
    worker {
        SyncWorker(androidApplication(), get())
    }
}