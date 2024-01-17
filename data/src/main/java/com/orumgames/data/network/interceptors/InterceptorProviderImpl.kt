package com.orumgames.data.network.interceptors

import com.orumgames.data.di.InterceptorProvider
import okhttp3.Interceptor
import javax.inject.Inject

internal class InterceptorProviderImpl @Inject constructor() : InterceptorProvider {

    override fun getHttpInterceptors(): List<Interceptor> = listOf(
    )
}