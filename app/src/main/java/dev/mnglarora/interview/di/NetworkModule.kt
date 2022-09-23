package dev.mnglarora.interview.di

import dev.mnglarora.interview.network.GhRepoServiceImpl
import io.ktor.client.*
import io.ktor.client.engine.android.*
import io.ktor.client.plugins.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.client.plugins.logging.*
import io.ktor.client.request.*
import io.ktor.http.*
import io.ktor.serialization.kotlinx.*
import io.ktor.serialization.kotlinx.json.*
import kotlinx.serialization.json.Json
import org.koin.dsl.module

private const val TIME_OUT = 60_000
val networkModule = module {
    single {
        HttpClient(Android) {
            install(Logging) {
                logger = Logger.ANDROID
                level = LogLevel.ALL
            }
            install(ContentNegotiation) {
                json(Json {
                    ignoreUnknownKeys = true; isLenient = true
                })
                serialization(
                    ContentType.Application.Json,
                    Json { ignoreUnknownKeys = true; isLenient = true })
                serialization(
                    ContentType.parse("application/vnd.github+json"),
                    Json { ignoreUnknownKeys = true; isLenient = true })
            }
            install(DefaultRequest) {
                header(HttpHeaders.ContentType, "application/vnd.github+json")
            }
            engine {
                connectTimeout = TIME_OUT
                socketTimeout = TIME_OUT
            }
        }
    }

    single {
        GhRepoServiceImpl(get())
    }
}