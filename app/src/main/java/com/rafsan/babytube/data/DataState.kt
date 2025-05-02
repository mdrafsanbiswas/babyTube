package com.rafsan.babytube.data

sealed class DataState<out T> {
    object Loading : DataState<Nothing>()
    data class Success<T>(var data: T) : DataState<T>()
    data class Error(val message: String)
}