package com.tiagomissiato.challenge.core.network.di

import com.tiagomissiato.challenge.core.network.api.GitApiService
import com.tiagomissiato.wheredidistop.core.network.adapter.NetworkResultCallAdapterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {
    private const val BASE_URL = "https://api.github.com/"

    @Singleton
    @Provides
    fun provideLoggingInterceptor(): HttpLoggingInterceptor {
        val logInterceptor = HttpLoggingInterceptor()
        logInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY)
        return logInterceptor
    }


    @Singleton
    @Provides
    fun provideOkHttpClient(loggingInterceptor: HttpLoggingInterceptor) =
        OkHttpClient
            .Builder()
            .addInterceptor(loggingInterceptor)
            .build()

    @Singleton
    @Provides
    fun provideRetrofit(okHttpClient: OkHttpClient): Retrofit =
        Retrofit
            .Builder()
            .client(okHttpClient)
            .addConverterFactory(MoshiConverterFactory.create())
            .addCallAdapterFactory(NetworkResultCallAdapterFactory.create())
            .baseUrl(BASE_URL)
            .build()

    @Singleton
    @Provides
    fun provideApiService(retrofit: Retrofit): GitApiService =
        retrofit.create(GitApiService::class.java)
}

