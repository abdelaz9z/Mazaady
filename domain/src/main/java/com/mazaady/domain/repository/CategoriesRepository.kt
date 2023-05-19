package com.mazaady.domain.repository

import com.mazaady.domain.entity.cats.CategoriesResponse
import com.mazaady.domain.entity.cat.SubCategoryResponse

interface CategoriesRepository {
    suspend fun getCategoriesFromRemote(): CategoriesResponse
    suspend fun getSubCategoryFromRemote(subCategoryId: Int): SubCategoryResponse
    suspend fun getOptionFromRemote(optionId: Int): SubCategoryResponse
}