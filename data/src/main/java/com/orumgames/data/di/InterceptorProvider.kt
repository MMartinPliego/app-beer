package com.orumgames.data.di

import okhttp3.Interceptor

interface InterceptorProvider {

    fun getHttpInterceptors(): List<Interceptor>
}