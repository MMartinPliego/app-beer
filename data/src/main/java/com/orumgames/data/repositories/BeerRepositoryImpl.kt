package com.orumgames.data.repositories

import com.orumgames.data.datasources.BeerRemoteDataSource
import com.orumgames.data.mappers.toBeer
import com.orumgames.domain.common.error.model.DomainError
import com.orumgames.domain.common.repositories.BeerRepository
import com.orumgames.domain.common.usecase.flow.Either
import com.orumgames.domain.common.usecase.flow.eitherSuccess
import com.orumgames.domain.models.Beer
import javax.inject.Inject

class BeerRepositoryImpl @Inject constructor(
    private val beerRemoteDataSource: BeerRemoteDataSource,
) : BeerRepository {
    override suspend fun getAllBeer(page: Int): Either<List<Beer>, DomainError> {
        return when (val res = beerRemoteDataSource.getAllBeers(page)) {
            is Either.Failure -> res
            is Either.Success -> eitherSuccess(res.data.map { it.toBeer() })
        }
    }

    override suspend fun getDetailBeer(idBeer: Int): Either<List<Beer>, DomainError> {
        return when (val res = beerRemoteDataSource.getDetailBeers(idBeer)) {
            is Either.Failure -> res
            is Either.Success -> eitherSuccess(res.data.map { it.toBeer() })
        }
    }
}