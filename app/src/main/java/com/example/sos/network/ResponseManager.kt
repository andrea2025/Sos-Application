package com.example.sos.network

sealed class ResponseManager<out T> {
    data class Loading(val state: Boolean = true): ResponseManager<Nothing>()
    data class Failure(val error: Throwable? = null): ResponseManager<Nothing>()
    data class Success<T>(val data: T): ResponseManager<T>()

}