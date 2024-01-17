package com.orumgames.data.error

import com.orumgames.domain.common.error.GlobalErrorMapper
import com.orumgames.domain.common.error.GlobalErrorType
import com.orumgames.domain.common.exception.NetworkException
import com.orumgames.domain.common.exception.UnauthorizedException
import javax.inject.Inject
import kotlin.coroutines.cancellation.CancellationException

internal class GlobalErrorMapperImpl @Inject constructor() : GlobalErrorMapper {

    override fun map(throwable: Throwable): GlobalErrorType =
        when (throwable) {
            is UnauthorizedException -> GlobalErrorType.UNAUTHORIZED
            is NetworkException -> GlobalErrorType.NETWORK_UNAVAILABLE
            is IllegalStateException, is CancellationException -> GlobalErrorType.SILENT
            else -> GlobalErrorType.GENERIC_ERROR
        }
}
