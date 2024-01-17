package com.orumgames.data.di

import com.orumgames.data.common.SERVICE_URL_BASE
import com.orumgames.data.network.BASE_URL
import com.orumgames.data.network.service.BeersService
import com.squareup.moshi.Moshi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import javax.inject.Named
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
internal object NetworkModule {


    @Singleton
    @Provides
    fun providesHttpLoggingInterceptor() = HttpLoggingInterceptor()
        .apply {
            level = HttpLoggingInterceptor.Level.BODY
        }

    @Singleton
    @Provides
    fun providesOkHttpClient(
        httpLoggingInterceptor: HttpLoggingInterceptor,
    ): OkHttpClient {
        val client = OkHttpClient
            .Builder()
        client.addInterceptor(httpLoggingInterceptor)
        return client.build()
    }


    @Singleton
    @Provides
    fun provideRetrofit(okHttpClient: OkHttpClient, @Named(SERVICE_URL_BASE) baseUrl: String, moshi: Moshi): Retrofit =
        Retrofit.Builder()
            .addConverterFactory(MoshiConverterFactory.create(moshi))
            .baseUrl(baseUrl)
            .client(okHttpClient)
            .build()

    @Singleton
    @Provides
    fun provideApiService(retrofit: Retrofit): BeersService = retrofit.create(BeersService::class.java)

    @Provides
    @Singleton
    @Named(SERVICE_URL_BASE)
    @Suppress("FunctionOnlyReturningConstant")
    internal fun provideBaseUrl() = BASE_URL

}