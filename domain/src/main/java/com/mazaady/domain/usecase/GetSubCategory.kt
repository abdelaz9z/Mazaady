package com.mazaady.domain.usecase

import com.mazaady.domain.repository.CategoriesRepository

class GetSubCategory(private val categoriesRepository: CategoriesRepository) {
    suspend operator fun invoke(subCategoryId: Int) =
        categoriesRepository.getSubCategoryFromRemote(subCategoryId)
}