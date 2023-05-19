package com.mazaady.data.remote

import com.mazaady.domain.entity.cats.CategoriesResponse
import com.mazaady.domain.entity.cat.SubCategoryResponse
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {
    @Headers("private-key:3%o8i}_;3D4bF]G5@22r2)Et1&mLJ4?$@+16")
    @GET("get_all_cats")
    suspend fun getAllCats(): CategoriesResponse

    @Headers("private-key:3%o8i}_;3D4bF]G5@22r2)Et1&mLJ4?$@+16")
    @GET("properties")
    suspend fun subCategoryId(@Query("cat") subCategoryId: Int?): SubCategoryResponse

    @Headers("private-key:3%o8i}_;3D4bF]G5@22r2)Et1&mLJ4?$@+16")
    @GET("get-options-child/{option_id}")
    suspend fun optionId(@Path("option_id") optionId: Int?): SubCategoryResponse
}