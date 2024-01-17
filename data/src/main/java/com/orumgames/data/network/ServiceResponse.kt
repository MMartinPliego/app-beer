package com.orumgames.data.network

sealed class ServiceResponse<out T> {
    data class Success<out T>(val data: T): ServiceResponse<T>()
    data class BadRequest(val error: String?): ServiceResponse<Nothing>()
    data class ServerError(val error: String?): ServiceResponse<Nothing>()
    object NoDataResponse : ServiceResponse<Nothing>()
    object Unauthorized : ServiceResponse<Nothing>()
    object Timeout : ServiceResponse<Nothing>()
}
