package com.orumgames.data.mappers

import com.orumgames.data.model.BeerDTO
import com.orumgames.data.network.ServiceResponse
import com.orumgames.domain.common.error.model.DomainError
import com.orumgames.domain.common.usecase.flow.Either
import com.orumgames.domain.common.usecase.flow.eitherFailure
import com.orumgames.domain.common.usecase.flow.eitherSuccess
import com.orumgames.domain.models.Beer

fun <T> ServiceResponse<T>.toDomain(): Either<T, DomainError> {
    return when (this) {
        is ServiceResponse.Success -> eitherSuccess(this.data)
        is ServiceResponse.ServerError,
        is ServiceResponse.BadRequest, -> eitherFailure(DomainError.ServerError)
        is ServiceResponse.NoDataResponse -> eitherFailure(DomainError.NoDataError)
        is ServiceResponse.Timeout -> eitherFailure(DomainError.TimeoutError)
        else -> eitherFailure(DomainError.ServerError)
    }
}

fun BeerDTO.toBeer() =
    Beer(
       id,
        name,
        description,
        imageUrl,
        abv
    )
