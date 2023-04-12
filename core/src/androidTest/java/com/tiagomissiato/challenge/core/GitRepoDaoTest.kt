@file:OptIn(ExperimentalCoroutinesApi::class)

package com.tiagomissiato.challenge.core

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.tiagomissiato.challenge.core.database.AppDatabase
import com.tiagomissiato.challenge.core.database.dao.GitRepoDao
import com.tiagomissiato.challenge.core.mock.GitRepoDataMock
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import java.io.IOException

@RunWith(AndroidJUnit4::class)
class GitRepoDaoTest {

    private lateinit var getRepoDao: GitRepoDao
    private lateinit var db: AppDatabase

    @Before
    fun createDb() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        db = Room.inMemoryDatabaseBuilder(
            context, AppDatabase::class.java)
            .allowMainThreadQueries()
            .build()

        getRepoDao = db.repositoryDao()
    }

    @After
    @Throws(IOException::class)
    fun closeDb() {
        db.close()
    }

    @Test
    @Throws(Exception::class)
    fun insert_insertOneItem() = runTest {
        //given
        val entityList = GitRepoDataMock.getGitRepoEntity(1)

        //when
        getRepoDao.insertAll(entityList)
        val list = getRepoDao.getAll()

        //then
        assertEquals(list.first(), entityList.first())
    }

    @Test
    @Throws(Exception::class)
    fun select_selectItemsSortedByStars() = runTest {
        //given
        val entityList = GitRepoDataMock.getGitRepoEntity(3)
        val maxStar = entityList.maxBy { it.starCount  }

        //when
        getRepoDao.insertAll(entityList)
        val list = getRepoDao.getAll()

        //then
        assertEquals(list.first(), maxStar)
    }

    @Test
    @Throws(Exception::class)
    fun delete_deleteOneItem() = runTest {
        //given
        val entityList = GitRepoDataMock.getGitRepoEntity(3)
        val toDelete = entityList[1]

        //when
        getRepoDao.insertAll(entityList)
        val list = getRepoDao.getAll()

        //then
        assertEquals(list.size, 3)

        //when
        getRepoDao.delete(toDelete)
        val newList = getRepoDao.getAll()
        assertEquals(newList.size, 2)
        assertFalse(newList.any { it == toDelete })
    }

    @Test
    @Throws(Exception::class)
    fun delete_deleteAllItems() = runTest {
        //given
        val entityList = GitRepoDataMock.getGitRepoEntity(3)
        val maxStar = entityList.maxBy { it.starCount  }

        //when
        getRepoDao.insertAll(entityList)
        val list = getRepoDao.getAll()

        //then
        assertEquals(list.size, 3)

        //when
        getRepoDao.deleteAll()
        val newList = getRepoDao.getAll()
        assertTrue(newList.isEmpty())
    }
}