package com.digel.recipe.domain

import com.digel.recipe.data.ResponseGetRecipe
import com.digel.recipe.utils.ResultState
import kotlinx.coroutines.flow.StateFlow

interface RecipeRepository {

    val recipeList: StateFlow<ResultState<List<ResponseGetRecipe>>>
    suspend fun getRecipe()
    fun postErrorProductList(throwable: Throwable)
}