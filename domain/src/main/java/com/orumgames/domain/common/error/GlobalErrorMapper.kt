package com.orumgames.domain.common.error

interface GlobalErrorMapper {
    fun map(throwable: Throwable): GlobalErrorType
}