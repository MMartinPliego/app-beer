package com.orumgames.data.datasources

import com.orumgames.data.common.PAGE_COUNT
import com.orumgames.data.common.mapToApiResponse
import com.orumgames.data.common.toDomain
import com.orumgames.data.model.BeerDTO
import com.orumgames.data.network.service.BeersService
import com.orumgames.domain.common.error.model.DomainError
import com.orumgames.domain.common.usecase.flow.Either
import com.orumgames.domain.common.usecase.flow.eitherFailure
import com.orumgames.domain.common.usecase.flow.eitherSuccess
import java.net.UnknownHostException
import javax.inject.Inject


class BeerRemoteDataSource @Inject constructor(private val beersService: BeersService) {

    suspend fun getAllBeers(page: Int) : Either<List<BeerDTO>, DomainError> {
        return try {
            val response = beersService.getAllBeers(page, PAGE_COUNT)
            when (val res = response.mapToApiResponse().toDomain()) {
                is Either.Failure -> res
                is Either.Success -> eitherSuccess(res.data)
            }
        } catch (ex: UnknownHostException) {
            eitherFailure(DomainError.ConnectivityError)
        } catch (ex: Exception) {
            eitherFailure(DomainError.GenericExceptionError(ex.message))
        }
    }

    suspend fun getDetailBeers(idBeer: Int) : Either<List<BeerDTO>, DomainError> {
        return try {
            val response = beersService.getDetailBeer(idBeer)
            when (val res = response.mapToApiResponse().toDomain()) {
                is Either.Failure -> res
                is Either.Success -> eitherSuccess(res.data)
            }
        } catch (ex: UnknownHostException) {
            eitherFailure(DomainError.ConnectivityError)
        } catch (ex: Exception) {
            eitherFailure(DomainError.GenericExceptionError(ex.message))
        }
    }

}