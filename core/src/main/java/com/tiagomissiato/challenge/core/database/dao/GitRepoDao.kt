package com.tiagomissiato.challenge.core.database.dao

import androidx.room.*
import com.tiagomissiato.challenge.core.database.entity.GitRepoEntity

@Dao
interface GitRepoDao {

    @Query("SELECT * FROM gitrepoentity ORDER BY repo_stars DESC")
    suspend fun getAll(): List<GitRepoEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    @JvmSuppressWildcards
    suspend fun insertAll(movies: List<GitRepoEntity>)

    @Delete
    suspend fun delete(movie: GitRepoEntity)

    @Query("DELETE FROM gitrepoentity")
    suspend fun deleteAll()

}