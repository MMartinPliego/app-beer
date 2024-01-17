package com.orumgames.data.datasources

import android.content.SharedPreferences
import com.orumgames.domain.common.error.model.DomainError
import com.orumgames.domain.common.usecase.flow.Either
import com.orumgames.domain.common.usecase.flow.eitherFailure
import com.orumgames.domain.common.usecase.flow.eitherSuccess
import com.squareup.moshi.Moshi
import java.io.IOException

interface SharedPreferencesDataSource {

    fun <T> store(key: String, data: T?, clazz: Class<T>): Either<Unit, DomainError>
    fun <T> get(key: String, clazz: Class<T>): Either<T, DomainError>
    fun remove(key: String): Either<Unit, DomainError>
    fun clear(): Either<Unit, DomainError>
    fun setOnboard(showOnboard: Boolean) : Either<Unit, DomainError>
    fun getOnboard(): Boolean
}

internal class SharedPreferencesDataSourceImpl(
    private val sharedPreferences: SharedPreferences,
    private val moshi: Moshi
) : SharedPreferencesDataSource {

    override fun <T> store(key: String, data: T?, clazz: Class<T>): Either<Unit, DomainError> {
        return try {
            data?.let {
                putString(key, moshi.adapter(clazz).toJson(data))
            } ?: run {
                putString(key, null)
            }
            eitherSuccess(Unit)
        } catch (e: IOException) {
            eitherFailure(DomainError.GenericExceptionError(e.stackTraceToString()))
        } catch (e: Exception) {
            eitherFailure(DomainError.GenericExceptionError(e.stackTraceToString()))
        }
    }

    override fun <T> get(key: String, clazz: Class<T>): Either<T, DomainError> {
        return try {
            getString(key)?.let {
                moshi.adapter(clazz).fromJson(it)
            }?.let { eitherSuccess(it) } ?: eitherFailure(DomainError.MissingLocalStorageError)
        } catch (e: IOException) {
            eitherFailure(DomainError.GenericExceptionError(e.stackTraceToString()))
        } catch (e: Exception) {
            eitherFailure(DomainError.GenericExceptionError(e.stackTraceToString()))
        }
    }

    override fun remove(key: String): Either<Unit, DomainError> {
        return try {
            eitherSuccess(sharedPreferences.edit().remove(key).apply())
        } catch (e: IOException) {
            eitherFailure(DomainError.GenericExceptionError(e.stackTraceToString()))
        } catch (e: Exception) {
            eitherFailure(DomainError.GenericExceptionError(e.stackTraceToString()))
        }
    }

    override fun clear(): Either<Unit, DomainError> {
        return try {
            eitherSuccess(sharedPreferences.edit().clear().apply())
        } catch (e: IOException) {
            eitherFailure(DomainError.GenericExceptionError(e.stackTraceToString()))
        } catch (e: Exception) {
            eitherFailure(DomainError.GenericExceptionError(e.stackTraceToString()))
        }
    }

    private fun putString(key: String, value: String?) {
        sharedPreferences.edit().putString(key, value).apply()
    }

    private fun getString(key: String) = sharedPreferences.getString(key, null)

    override fun setOnboard(showOnboard: Boolean): Either<Unit, DomainError> {
        return try {
            eitherSuccess(sharedPreferences.edit().putBoolean(SHOW_ONBOARD, showOnboard).apply())
        } catch (e: IOException) {
            eitherFailure(DomainError.GenericExceptionError(e.stackTraceToString()))
        } catch (e: Exception) {
            eitherFailure(DomainError.GenericExceptionError(e.stackTraceToString()))
        }
    }

    override fun getOnboard(): Boolean = sharedPreferences.getBoolean(SHOW_ONBOARD, true)

    companion object {
        private const val SHOW_ONBOARD = "SHOW_ONBOARD"
    }
}
