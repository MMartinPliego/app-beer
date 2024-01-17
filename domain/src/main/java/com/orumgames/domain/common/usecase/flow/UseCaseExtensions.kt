package com.orumgames.domain.common.usecase.flow

fun <R> FlowUseCase<Unit, R>.execute() = execute(Unit)