package dev.mnglarora.interview.persistent

import androidx.room.*
import dev.mnglarora.interview.model.GhRepo
import kotlinx.coroutines.flow.Flow

@Dao
interface GhRepoDao {

    @Query("SELECT * from GhRepo")
    fun getTrendingRepos(): Flow<List<GhRepo>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertOrUpdate(repositories: List<GhRepo>)

    @Query("DELETE FROM GhRepo WHERE id in (:ids)")
    suspend fun delete(ids: List<Int>)

    @Query("SELECT * from GhRepo where name LIKE :param")
    fun searchRepo(param: String) : Flow<List<GhRepo>>

}