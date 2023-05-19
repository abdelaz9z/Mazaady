package com.mazaady.mazaady.ui.main

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mazaady.domain.entity.cat.SubCategoryResponse
import com.mazaady.domain.entity.cats.CategoriesResponse
import com.mazaady.domain.usecase.GetCategories
import com.mazaady.domain.usecase.GetOption
import com.mazaady.domain.usecase.GetSubCategory
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val getCategoriesUseCase: GetCategories,
    private val getSubCategoryUseCase: GetSubCategory,
    private val getOptionUseCase: GetOption
) :
    ViewModel() {

    private val _categories: MutableStateFlow<CategoriesResponse?> = MutableStateFlow(null)
    val categories: StateFlow<CategoriesResponse?> = _categories

    private val _subCategory: MutableStateFlow<SubCategoryResponse?> = MutableStateFlow(null)
    val subCategory: StateFlow<SubCategoryResponse?> = _subCategory

    private val _options: MutableStateFlow<SubCategoryResponse?> = MutableStateFlow(null)
    val options: StateFlow<SubCategoryResponse?> = _options

    /**
     * Get categories: get all cats
     */
    fun getCategories() {
        viewModelScope.launch {
            try {
                _categories.value = getCategoriesUseCase()
            } catch (e: Exception) {
                Log.e(TAG, e.message.toString())
            }
        }
    }

    /**
     * Get sub category: cat = ...
     *
     * @param subCategoryId sub category id
     */
    fun getSubCategory(subCategoryId: Int) {
        viewModelScope.launch {
            try {
                _subCategory.value = getSubCategoryUseCase(subCategoryId)
            } catch (e: Exception) {
                Log.e(TAG, e.message.toString())
            }
        }
    }

    /**
     * Get option: get-options-child
     *
     * @param optionId option id
     */
    fun getOption(optionId: Int) {
        viewModelScope.launch {
            try {
                _options.value = getOptionUseCase(optionId)
            } catch (e: Exception) {
                Log.e(TAG, e.message.toString())
            }
        }
    }

    companion object {
        private val TAG = MainViewModel::class.simpleName
    }
}