package com.orumgames.domain.common.usecase

import com.orumgames.domain.common.error.model.DomainError
import com.orumgames.domain.common.repositories.BeerRepository
import com.orumgames.domain.common.test.MainCoroutinesRule
import com.orumgames.domain.common.usecase.flow.eitherFailure
import com.orumgames.domain.common.usecase.flow.eitherSuccess
import com.orumgames.domain.common.usecase.flow.isSuccess
import com.orumgames.domain.common.usecase.flow.onFailure
import com.orumgames.domain.common.usecase.flow.onSuccess
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.confirmVerified
import io.mockk.mockk
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import kotlin.test.assertTrue

class LoadBeersUseCaseTest {


    private val beerRepository: BeerRepository = mockk()

    @get:Rule
    val dispatcherProvider = MainCoroutinesRule()

    lateinit var loadBeersUseCase: LoadBeersUseCase

    @Before
    fun onBefore() {
        loadBeersUseCase = LoadBeersUseCase(beerRepository, dispatcherProvider.testDispatcherProvider)
    }

    @Test
    fun `list Beers Return For Api`() = runTest {
        coEvery { beerRepository.getAllBeer(any()) } returns eitherSuccess(listOf(mockk()))

        loadBeersUseCase.execute(0).collect {
            it.onSuccess { list ->
                assertTrue(list.isNotEmpty())
            }
            assertTrue(it.isSuccess)
        }

        coVerify { beerRepository.getAllBeer(any()) }

        confirmVerified(beerRepository)
    }

    @Test
    fun `empty List Beers Return For Api`() = runTest {
        coEvery { beerRepository.getAllBeer(any()) } returns eitherFailure(DomainError.ServerError)

        loadBeersUseCase.execute(0).collect {
            it.onFailure {
                DomainError.ServerError
            }
        }

        coVerify { beerRepository.getAllBeer(any()) }

        confirmVerified(beerRepository)
    }

}