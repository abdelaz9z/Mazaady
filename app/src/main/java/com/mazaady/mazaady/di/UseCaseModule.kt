package com.mazaady.mazaady.di

import com.mazaady.domain.repository.CategoriesRepository
import com.mazaady.domain.usecase.GetCategories
import com.mazaady.domain.usecase.GetOption
import com.mazaady.domain.usecase.GetSubCategory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object UseCaseModule {

    @Provides
    fun provideUseCaseCategories(mealsRepository: CategoriesRepository): GetCategories {
        return GetCategories(mealsRepository)
    }

    @Provides
    fun provideUseCaseSubCategory(mealsRepository: CategoriesRepository): GetSubCategory {
        return GetSubCategory(mealsRepository)
    }

    @Provides
    fun provideUseCaseOption(mealsRepository: CategoriesRepository): GetOption {
        return GetOption(mealsRepository)
    }
}