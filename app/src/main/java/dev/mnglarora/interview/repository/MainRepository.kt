package dev.mnglarora.interview.repository

import dev.mnglarora.interview.model.GhRepo
import dev.mnglarora.interview.network.GhRepoService
import dev.mnglarora.interview.network.utils.ApiResponse
import dev.mnglarora.interview.persistent.GhRepoDao
import kotlinx.coroutines.flow.Flow

class MainRepository constructor(
    private val githubService: GhRepoService,
    private val githubRepoDao: GhRepoDao
) : Repository {

    suspend fun getRepos(): ApiResponse<List<GhRepo>> = githubService.getTrendingRepos()

    suspend fun deleteLocalRepos(ids: List<Int>) = githubRepoDao.delete(ids)

    suspend fun saveReposToDatabase(list: List<GhRepo>) = githubRepoDao.insertOrUpdate(list)

    fun getLocalRepos(): Flow<List<GhRepo>> = githubRepoDao.getTrendingRepos()

}