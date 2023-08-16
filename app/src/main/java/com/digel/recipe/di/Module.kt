package com.digel.recipe.di

import com.digel.recipe.data.Service
import com.digel.recipe.domain.RecipeRepository
import com.digel.recipe.domain.RecipeRepositoryImpl
import com.digel.recipe.ui.viewmodel.RecipeViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val serviceModule = module {
    single { Service.create() }
}

val recipeRepository = module {
    factory<RecipeRepository> { RecipeRepositoryImpl(get()) }
}

val recipeViewModel = module { viewModel { RecipeViewModel(get()) } }