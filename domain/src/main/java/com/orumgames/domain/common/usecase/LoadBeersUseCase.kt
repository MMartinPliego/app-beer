package com.orumgames.domain.common.usecase

import com.orumgames.domain.common.error.model.DomainError
import com.orumgames.domain.common.repositories.BeerRepository
import com.orumgames.domain.common.usecase.dispatchers.DispatcherProvider
import com.orumgames.domain.common.usecase.flow.Either
import com.orumgames.domain.common.usecase.flow.FlowUseCase
import com.orumgames.domain.models.Beer
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class LoadBeersUseCase @Inject constructor(
    private val beerRepository: BeerRepository,
    dispatcherProvider: DispatcherProvider,
) : FlowUseCase<Int, Either<List<Beer>, DomainError>>(dispatcherProvider) {

    override fun start(param: Int): Flow<Either<List<Beer>, DomainError>> = flow {
        emit(beerRepository.getAllBeer(param))
    }
}


