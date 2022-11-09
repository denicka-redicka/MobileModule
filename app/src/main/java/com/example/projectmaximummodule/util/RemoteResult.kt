package com.example.projectmaximummodule.util

import java.util.concurrent.CancellationException

sealed class RemoteResult<out S, out E> {

    data class Success<out S>(val value: S): RemoteResult<S, Nothing>()

    data class Failed<out E>(val error: E): RemoteResult<Nothing, E>()
}

inline fun <S, R> S.runResultCatching(block: S.() -> R): RemoteResult<R, Throwable> {
    return try {
        RemoteResult.Success(block())
    } catch (e: CancellationException) {
        throw e
    } catch (e: Throwable) {
        RemoteResult.Failed(e)
    }
}