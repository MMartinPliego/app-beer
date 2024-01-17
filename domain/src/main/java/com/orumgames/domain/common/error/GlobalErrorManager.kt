package com.orumgames.domain.common.error

import com.orumgames.domain.common.error.model.GlobalErrorHandlerModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class GlobalErrorManager @Inject constructor(private val defaultErrorMapper: GlobalErrorMapper) {

    private val _event = MutableSharedFlow<GlobalErrorHandlerModel>()
    val event: SharedFlow<GlobalErrorHandlerModel> = _event.asSharedFlow()

    suspend fun emitError(
        throwable: Throwable,
        onGenericError: (suspend () -> Unit)? = null,
        onUnauthorized: (suspend () -> Unit)? = null,
        onNetworkUnavailable: (suspend () -> Unit)? = null,
        dismissAction: (() -> Unit)? = null
    ) {
        _event.emit(
            GlobalErrorHandlerModel(
                defaultErrorMapper.map(throwable),
                onGenericError,
                onUnauthorized,
                onNetworkUnavailable,
                dismissAction
            )
        )
    }
}