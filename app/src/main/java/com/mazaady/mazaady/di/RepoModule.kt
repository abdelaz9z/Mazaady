package com.mazaady.mazaady.di

import com.mazaady.data.remote.ApiService
import com.mazaady.data.repository.CategoriesRepositoryImpl
import com.mazaady.domain.repository.CategoriesRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object RepoModule {

    @Provides
    fun provideRepo(apiService: ApiService): CategoriesRepository {
        return CategoriesRepositoryImpl(apiService)
    }
}