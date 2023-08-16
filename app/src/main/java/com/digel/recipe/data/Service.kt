package com.digel.recipe.data

import retrofit2.Response
import retrofit2.http.GET

interface Service {

    companion object {
        fun create() : Service {
            return Network.build().create(Service::class.java)
        }
    }

    @GET
    suspend fun getRecipe() : Response<List<ResponseGetRecipe>>
}