package com.mazaady.data.repository

import com.mazaady.data.remote.ApiService
import com.mazaady.domain.entity.cats.CategoriesResponse
import com.mazaady.domain.entity.cat.SubCategoryResponse
import com.mazaady.domain.repository.CategoriesRepository

class CategoriesRepositoryImpl(private val apiService: ApiService) : CategoriesRepository {
    override suspend fun getCategoriesFromRemote(): CategoriesResponse = apiService.getAllCats()

    override suspend fun getSubCategoryFromRemote(subCategoryId: Int): SubCategoryResponse =
        apiService.subCategoryId(subCategoryId)

    override suspend fun getOptionFromRemote(optionId: Int): SubCategoryResponse =
        apiService.optionId(optionId)
}