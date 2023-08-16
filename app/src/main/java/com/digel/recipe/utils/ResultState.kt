package com.digel.recipe.utils

sealed class ResultState<T >{
    class Idle<T> : ResultState<T>()
    class Loading<T> : ResultState<T>()
    data class Success<T>(val data: T) : ResultState<T>()
    data class Failure<T>(val exception: Throwable) : ResultState<T>()
    class Empty<T> : ResultState<T>()
}