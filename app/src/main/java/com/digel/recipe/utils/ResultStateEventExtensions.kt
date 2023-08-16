package com.digel.recipe.utils

import retrofit2.Response
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

inline fun <reified T> ResultState<T>.doOnIdle(action: () -> Unit): ResultState<T> {
    if (this is ResultState.Idle) {
        action.invoke()
    }
    return this
}

inline fun <reified T> ResultState<T>.doOnLoading(action: () -> Unit): ResultState<T> {
    if (this is ResultState.Loading) {
        action.invoke()
    }
    return this
}

inline fun <reified T> ResultState<T>.doOnSuccess(data: (T) -> Unit): ResultState<T> {
    if (this is ResultState.Success) {
        data.invoke(this.data)
    }
    return this
}

inline fun <reified T> ResultState<T>.doOnFailure(failure: (Throwable) -> Unit): ResultState<T> {
    if (this is ResultState.Failure) {
        failure.invoke(this.exception)
    }
    return this
}

inline fun <reified T> ResultState<T>.doOnEmpty(failure: () -> Unit): ResultState<T> {
    if (this is ResultState.Empty) {
        failure.invoke()
    }
    return this
}

suspend inline fun <reified T> Response<T>.reduce(): ResultState<T> {
    return suspendCoroutine { task ->
        val emitData: ResultState<T> = try {
            val body = body()
            if (isSuccessful && body != null) {
                if (body is List<*> && body.isEmpty()) {
                    ResultState.Empty()
                } else {
                    ResultState.Success(body)
                }
            } else {
                val throwable = StateApiException(message(), code())
                ResultState.Failure(throwable)
            }
        } catch (e: Throwable) {
            ResultState.Failure(e)
        }

        task.resume(emitData)
    }
}