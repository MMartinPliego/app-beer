package com.orumgames.domain.common.usecase

import com.orumgames.domain.common.error.model.DomainError
import com.orumgames.domain.common.repositories.BeerRepository
import com.orumgames.domain.common.test.MainCoroutinesRule
import com.orumgames.domain.common.usecase.flow.eitherFailure
import com.orumgames.domain.common.usecase.flow.eitherSuccess
import com.orumgames.domain.common.usecase.flow.onFailure
import com.orumgames.domain.common.usecase.flow.onSuccess
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.confirmVerified
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import kotlin.test.assertTrue

class LoadDetailBeerUseCaseTest {

    private val beerRepository: BeerRepository = mockk()

    @get:Rule
    val dispatcherProvider = MainCoroutinesRule()

    lateinit var loadDetailBeersUseCase: LoadDetailBeerUseCase

    @Before
    fun onBefore() {
        loadDetailBeersUseCase = LoadDetailBeerUseCase(beerRepository, dispatcherProvider.testDispatcherProvider)
    }

    @Test
    fun `detail Beer Return For Api`() = runTest {
        coEvery { beerRepository.getDetailBeer(any()) } returns eitherSuccess(listOf(mockk()))

        loadDetailBeersUseCase.execute(0).collect {
            it.onSuccess { beer ->
                assertTrue(beer.isNotEmpty())
            }
        }

        coVerify { beerRepository.getDetailBeer(any()) }

        confirmVerified(beerRepository)
    }

    @Test
    fun `error Detail Beer Return For Api`() = runTest {
        coEvery { beerRepository.getDetailBeer(any()) } returns eitherFailure(DomainError.ServerError)

        loadDetailBeersUseCase.execute(0).collect {
            it.onFailure {
                DomainError.ServerError
            }
        }

        coVerify { beerRepository.getDetailBeer(any()) }

        confirmVerified(beerRepository)
    }
}