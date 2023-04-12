package com.tiagomissiato.wheredidistop.core.network.adapter

import retrofit2.HttpException
import retrofit2.Response

sealed class NetworkResultException( cause: Throwable?) : Exception(cause) {
    class Error(e: Throwable? = null, val code: Int, val msg: String?) : NetworkResultException(e)
    class Exception(e: Throwable) : NetworkResultException(e)
}

sealed class NetworkResult<T : Any> {
    class Success<T: Any>(val data: T) : NetworkResult<T>()
    class Error<T: Any>(val code: Int, val message: String?) : NetworkResult<T>()
    class Exception<T: Any>(val e: Throwable) : NetworkResult<T>()
}

fun <T : Any> Response<T>.handleApi(): NetworkResult<T> {
    return try {
        val body = body()
        if (isSuccessful && body != null) {
            NetworkResult.Success(body)
        } else {
            NetworkResult.Error(code = code(), message = message())
        }
    } catch (e: HttpException) {
        NetworkResult.Error(code = e.code(), message = e.message())
    } catch (e: Throwable) {
        NetworkResult.Exception(e)
    }
}