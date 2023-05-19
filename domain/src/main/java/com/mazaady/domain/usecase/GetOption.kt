package com.mazaady.domain.usecase

import com.mazaady.domain.repository.CategoriesRepository

class GetOption(private val categoriesRepository: CategoriesRepository) {
    suspend operator fun invoke(optionId: Int) =
        categoriesRepository.getOptionFromRemote(optionId)
}