package com.digel.recipe

import android.app.Application
import com.digel.recipe.di.recipeRepository
import com.digel.recipe.di.recipeViewModel
import com.digel.recipe.di.serviceModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class MainApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@MainApplication)
            modules(
                serviceModule,
                recipeRepository,
                recipeViewModel
            )
        }
    }
}