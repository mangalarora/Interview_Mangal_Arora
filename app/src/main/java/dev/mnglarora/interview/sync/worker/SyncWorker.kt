package dev.mnglarora.interview.sync.worker

import android.content.Context
import android.util.Log
import androidx.work.*
import dev.mnglarora.interview.network.utils.ApiResponse
import dev.mnglarora.interview.repository.MainRepository
import dev.mnglarora.interview.sync.utils.initNetworkMonitoring
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class SyncWorker(
    private val appContext: Context,
    params: WorkerParameters
) : CoroutineWorker(appContext, params = params), KoinComponent {

    private val mainRepo: MainRepository by inject()

    override suspend fun getForegroundInfo(): ForegroundInfo {
        return appContext.syncForegroundInfo()
    }

    override suspend fun doWork(): Result = withContext(Dispatchers.IO) {

        var list: List<Int> = arrayListOf()


        launch {
            mainRepo.getLocalRepos().collect { ghRepoList ->
                list = ghRepoList.map { it.id }
            }
        }

        /**
         * Refresh Data in every 5 minutes
         */

        while (true) {

            //Write All The Errors to Logs or Firebase Crashlytics
            when (val resp = mainRepo.getRepos()) {
                is ApiResponse.Failure -> {

                }
                is ApiResponse.HttpErrors.BadGateWay -> {

                }
                is ApiResponse.HttpErrors.InternalServerError -> {

                }
                is ApiResponse.HttpErrors.RemovedResourceFound -> {

                }
                is ApiResponse.HttpErrors.ResourceForbidden -> {

                }
                is ApiResponse.HttpErrors.ResourceNotFound -> {

                }
                is ApiResponse.HttpErrors.ResourceRemoved -> {

                }
                ApiResponse.Loading -> {

                }
                is ApiResponse.NetworkException -> {

                }
                is ApiResponse.Success -> {
                    try {
                        val finalList = arrayListOf<Int>()
                        finalList.addAll(list)
                        val res = resp.data.map { it.id }
                        val idsToDelete = finalList.toSet().minus(res.toSet())
                        //Overwriting Data Because its just matter of 25 Records

                        if (finalList.isEmpty() && idsToDelete.isEmpty()) {
                            val sortedDataList = resp.data.mapIndexed { index, ghRepo ->
                                ghRepo.apply {
                                    rank = index
                                }
                            }
                            mainRepo.saveReposToDatabase(sortedDataList)
                        } else if (finalList.isNotEmpty() && idsToDelete.isNotEmpty()) {
                            val sortedDataList = resp.data.mapIndexed { index, ghRepo ->
                                ghRepo.apply {
                                    rank = index
                                }
                            }
                            mainRepo.saveReposToDatabase(sortedDataList)
                            mainRepo.deleteLocalRepos(idsToDelete.toList())
                        }

                    } catch (e: java.lang.Exception) {
                        e.localizedMessage?.let { Log.e("Worker", it) }
                    }
                }
            }

            delay(300000L)//Refresh In 5 Minutes

        }

        return@withContext Result.success()
    }

    companion object {
        /**
         * Expedited one time work to sync data on app startup
         */
        fun startUpSyncWork() = OneTimeWorkRequestBuilder<SyncWorker>()
            .setExpedited(OutOfQuotaPolicy.RUN_AS_NON_EXPEDITED_WORK_REQUEST)
            .setConstraints(SyncConstraints)
            .build()
    }
}