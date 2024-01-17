package com.orumgames.domain.common.usecase.flow

import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch

class FlowObserverDelegate<T>(
    lifecycleOwner: LifecycleOwner,
    private val flow: Flow<T>,
    private val collector: suspend (T) -> Unit
) {
    private var job: Job? = null

    init {
        lifecycleOwner.lifecycle.addObserver(
            LifecycleEventObserver { source: LifecycleOwner, event: Lifecycle.Event ->
                when(event) {
                    Lifecycle.Event.ON_START -> {
                        job = source.lifecycleScope.launch { flow.collect { collector(it) } }
                    }
                    Lifecycle.Event.ON_STOP -> {
                        job?.cancel()
                        job = null
                    }
                    else -> {}
                }
            }
        )
    }
}

inline fun <reified T> Flow<T>.launchInLifeCycle(lifecycleOwner: LifecycleOwner) =
    FlowObserverDelegate(lifecycleOwner, this, {})

inline fun <reified T> Flow<T>.collectInLifeCycle(
    lifecycleOwner: LifecycleOwner,
    noinline collector: suspend (T) -> Unit
) = FlowObserverDelegate(lifecycleOwner, this, collector)