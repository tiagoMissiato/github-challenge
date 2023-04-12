package com.tiagomissiato.challenge.repository.di

import com.tiagomissiato.challenge.repository.data.GitRepoRepositoryImpl
import com.tiagomissiato.challenge.repository.domain.GitRepoRepository
import com.tiagomissiato.challenge.repository.domain.RepositoryInteractor
import com.tiagomissiato.challenge.repository.domain.RepositoryInteractorImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
interface RepositoryModule {

    @Binds
    @Singleton
    fun bindRepoRepository(repository: GitRepoRepositoryImpl): GitRepoRepository

    @Binds
    @Singleton
    fun bindRepoInteractor(interactor: RepositoryInteractorImpl): RepositoryInteractor

}