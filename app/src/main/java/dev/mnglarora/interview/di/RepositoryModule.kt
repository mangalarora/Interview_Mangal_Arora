package dev.mnglarora.interview.di

import dev.mnglarora.interview.network.GhRepoServiceImpl
import dev.mnglarora.interview.repository.MainRepository
import org.koin.dsl.module

val repositoryModule = module {

    single { MainRepository(get<GhRepoServiceImpl>(), get()) }

}