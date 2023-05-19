package com.mazaady.domain.usecase

import com.mazaady.domain.repository.CategoriesRepository

class GetCategories(private val categoriesRepository: CategoriesRepository) {
    suspend operator fun invoke() = categoriesRepository.getCategoriesFromRemote()
}