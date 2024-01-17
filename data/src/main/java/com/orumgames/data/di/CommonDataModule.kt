package com.orumgames.data.di

import android.content.SharedPreferences
import com.orumgames.data.datasources.PrivateSharedPreferencesDataSource
import com.orumgames.data.datasources.PrivateSharedPreferencesDataSourceImpl
import com.orumgames.data.datasources.SharedPreferencesDataSource
import com.orumgames.data.datasources.SharedPreferencesDataSourceImpl
import com.orumgames.data.error.GlobalErrorMapperImpl
import com.orumgames.data.repositories.BeerRepositoryImpl
import com.orumgames.domain.common.common.ENCRYPTED_SHARED_PREFERENCES
import com.orumgames.domain.common.common.SHARED_PREFERENCES
import com.orumgames.domain.common.error.GlobalErrorMapper
import com.orumgames.domain.common.repositories.BeerRepository
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Named
import javax.inject.Singleton

@Module(includes = [CommonDataModule.DataSubModule::class])
@InstallIn(SingletonComponent::class)
internal object CommonDataModule {

    @Provides
    @Singleton
    internal fun provideGlobalErrorMapper(): GlobalErrorMapper = GlobalErrorMapperImpl()

    @Provides
    internal fun provideSharedPreferencesDataSource(
        @Named(SHARED_PREFERENCES) sharedPreferences: SharedPreferences,
        moshi: Moshi
    ): SharedPreferencesDataSource = SharedPreferencesDataSourceImpl(sharedPreferences, moshi)

    @Provides
    internal fun providePrivateSharedPreferencesDataSource(
        @Named(ENCRYPTED_SHARED_PREFERENCES) sharedPreferences: SharedPreferences,
        moshi: Moshi
    ): PrivateSharedPreferencesDataSource = PrivateSharedPreferencesDataSourceImpl(sharedPreferences, moshi)

    @Provides
    internal fun provideMoshi(): Moshi = Moshi
        .Builder()
        .add(KotlinJsonAdapterFactory())
        .build()

    @Module
    @InstallIn(SingletonComponent::class)
    internal interface DataSubModule {

        @Binds
        fun bindAllBeerRepository(impl: BeerRepositoryImpl): BeerRepository
    }
}