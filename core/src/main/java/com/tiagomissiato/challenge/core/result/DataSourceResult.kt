package com.tiagomissiato.challenge.core.result

sealed class DataSourceResult<T : Any> {
    class Success<T: Any>(val data: T) : DataSourceResult<T>()
    class Error<T: Any>(val data: T, val message: String?) : DataSourceResult<T>()
}