package com.orumgames.domain.common.repositories

import com.orumgames.domain.common.error.model.DomainError
import com.orumgames.domain.common.usecase.flow.Either
import com.orumgames.domain.models.Beer

interface BeerRepository {

    suspend fun getAllBeer(page: Int): Either<List<Beer>, DomainError>
    suspend fun getDetailBeer(idBeer: Int): Either<List<Beer>, DomainError>

}