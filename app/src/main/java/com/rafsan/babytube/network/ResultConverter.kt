package com.rafsan.babytube.network

import com.google.gson.GsonBuilder
import com.google.gson.reflect.TypeToken
import okhttp3.ResponseBody
import retrofit2.HttpException
import retrofit2.Response

inline fun <reified T : Any> Response<ResponseBody>.convert(): T {
    val body = if (this.isSuccessful) {
        this.body()?.string()
    } else {
        throw HttpException(this)
    }

    return GsonBuilder().serializeNulls().create().fromJson(
        body,
        object : TypeToken<T>() {}.type
    )
}

inline fun <reified T : Any> ResponseBody.convert(): T {
    return GsonBuilder().serializeNulls().create().fromJson(
        this.string(),
        T::class.java
    )
}