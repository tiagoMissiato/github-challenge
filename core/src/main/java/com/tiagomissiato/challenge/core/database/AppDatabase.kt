package com.tiagomissiato.challenge.core.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.tiagomissiato.challenge.core.database.dao.GitRepoDao
import com.tiagomissiato.challenge.core.database.entity.GitRepoEntity

@Database(entities = [GitRepoEntity::class], version = 2)
abstract class AppDatabase: RoomDatabase() {
    abstract fun repositoryDao(): GitRepoDao
}