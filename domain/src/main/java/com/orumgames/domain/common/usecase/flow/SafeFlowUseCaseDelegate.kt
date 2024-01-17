package com.orumgames.domain.common.usecase.flow

import com.orumgames.domain.common.error.GlobalErrorManager
import kotlinx.coroutines.flow.catch
import javax.inject.Inject

/** Delegate to call useCases in a safe way, because handle exceptions by default */
interface SafeFlowUseCaseDelegate {
    val globalErrorManager: GlobalErrorManager

    /** Method that handle global errors by default. Use lambdas for special cases */
    fun <T, R> FlowUseCase<T, R>.safeExecute(
        input: T,
        onGenericError: (suspend () -> Unit)? = null,
        onUnauthorized: (suspend () -> Unit)? = null,
        onNetworkUnavailable: (suspend () -> Unit)? = null,
        dismissAction: (() -> Unit)? = null
    ) = execute(input).catch {
        globalErrorManager.emitError(it, onGenericError, onUnauthorized, onNetworkUnavailable, dismissAction)
    }

    /** Method that handle global errors by default without parameter for use case. Use lambdas for special cases */
    fun <R> FlowUseCase<Unit, R>.safeExecute(
        onGenericError: (suspend () -> Unit)? = null,
        onUnauthorized: (suspend () -> Unit)? = null,
        onNetworkUnavailable: (suspend () -> Unit)? = null,
        dismissAction: (() -> Unit)? = null
    ) = execute(Unit).catch {
        globalErrorManager.emitError(it, onGenericError, onUnauthorized, onNetworkUnavailable, dismissAction)
    }

    class Default @Inject constructor(override val globalErrorManager: GlobalErrorManager) :
        SafeFlowUseCaseDelegate
}