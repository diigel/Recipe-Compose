package com.digel.recipe.domain

import com.digel.recipe.data.ResponseGetRecipe
import com.digel.recipe.data.Service
import com.digel.recipe.utils.ResultState
import com.digel.recipe.utils.reduce
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class RecipeRepositoryImpl(private val service: Service) : RecipeRepository {

    private val _getRecipeList: MutableStateFlow<ResultState<List<ResponseGetRecipe>>>
        get() = MutableStateFlow(ResultState.Idle())

    override val recipeList: StateFlow<ResultState<List<ResponseGetRecipe>>>
        get() = _getRecipeList

    override suspend fun getRecipe() {
        _getRecipeList.value = ResultState.Loading()
        service.getRecipe().reduce().also { result ->
            _getRecipeList.value = result
        }
    }

    override fun postErrorProductList(throwable: Throwable) {
        if (recipeList.value is ResultState.Loading) {
            _getRecipeList.value = ResultState.Failure(throwable)
        }
    }

}