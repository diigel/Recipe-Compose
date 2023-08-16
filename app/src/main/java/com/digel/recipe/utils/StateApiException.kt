package com.digel.recipe.utils

class StateApiException(message: String, private val code: Int) : Throwable(message) {
    fun code() = code
}