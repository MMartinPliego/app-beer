package com.orumgames.domain.common.di

import com.orumgames.domain.common.usecase.dispatchers.DefaultDispatcherProvider
import com.orumgames.domain.common.usecase.dispatchers.DispatcherProvider
import com.orumgames.domain.common.usecase.flow.SafeFlowUseCaseDelegate
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
interface CommonDomainModule {

    @Binds
    fun bindSafeFlowUseCaseDelegateProvider(default: SafeFlowUseCaseDelegate.Default): SafeFlowUseCaseDelegate

    @Binds
    @Singleton
    fun bindDispatcherProvider(defaultDispatcherProvider: DefaultDispatcherProvider): DispatcherProvider
}