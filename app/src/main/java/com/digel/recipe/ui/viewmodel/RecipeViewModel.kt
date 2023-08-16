package com.digel.recipe.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.digel.recipe.domain.RecipeRepository
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.plus

class RecipeViewModel(private val recipeRepository: RecipeRepository) : ViewModel() {

    private val recipeListErrorHandler = CoroutineExceptionHandler { _, throwable ->
        recipeRepository.postErrorProductList(throwable)
    }

    private val recipeListSafeScope: CoroutineScope
        get() = viewModelScope + recipeListErrorHandler

    val recipeList
        get() = recipeRepository.recipeList.asLiveData(recipeListSafeScope.coroutineContext)

    val recipeListFlow
        get() = recipeRepository.recipeList

    fun getRecipeList() = recipeListSafeScope.launch {
        recipeRepository.getRecipe()
    }

}