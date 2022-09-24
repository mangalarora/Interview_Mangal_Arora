package dev.mnglarora.interview

import android.app.Application
import androidx.appcompat.app.AppCompatDelegate
import dev.mnglarora.interview.di.*
import dev.mnglarora.interview.sync.initializer.Sync
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.workmanager.koin.workManagerFactory
import org.koin.core.component.KoinComponent
import org.koin.core.context.startKoin
import org.koin.dsl.module

class LKInterviewApp : Application(), KoinComponent {

    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@LKInterviewApp)
            modules(networkModule)
            modules(persistentModule)
            modules(repositoryModule)
            modules(viewModelModule)
            //workManagerFactory()
            modules(workerModule)
        }
        Sync.initialize(context = this)
        AppCompatDelegate.setCompatVectorFromResourcesEnabled(true)
    }

}