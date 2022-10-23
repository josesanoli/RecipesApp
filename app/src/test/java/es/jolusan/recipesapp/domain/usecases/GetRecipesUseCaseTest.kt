package es.jolusan.recipesapp.domain.usecases

import es.jolusan.recipesapp.domain.repositories.RecipeRepository
import es.jolusan.recipesapp.utils.ResponseStatus
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.flow.count
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.last
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test

class GetRecipesUseCaseTest {

    @MockK
    lateinit var recipesRepository: RecipeRepository

    lateinit var getRecipesUseCase: GetRecipesUseCase

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        getRecipesUseCase = GetRecipesUseCase(recipesRepository)
    }

    @Test
    fun `when repo returns success then resource should be success`() {
        // Given
        mockSuccess()
        runBlocking {
            // When
            val favoriteUseCase = getRecipesUseCase("")

            // Then
            val eventCount = favoriteUseCase.count()
            assert(eventCount == 2)

            var resource = favoriteUseCase.first()
            assert(resource is ResponseStatus.Loading)

            resource = favoriteUseCase.last()
            assert(resource is ResponseStatus.Success)
            assert(resource.data != null)
            println(resource)
        }
    }

    @Test
    fun `when repo returns error then resource should be error`() {
        // Given
        mockError()
        runBlocking {
            // When
            val popularMoviesUseCase = getRecipesUseCase("")

            // Then
            val eventCount = popularMoviesUseCase.count()
            assert(eventCount == 2)

            var resource = popularMoviesUseCase.first()
            assert(resource is ResponseStatus.Loading)

            resource = popularMoviesUseCase.last()
            assert(resource is ResponseStatus.Error)
            println(resource)
        }
    }

    private fun mockSuccess() {
        coEvery { recipesRepository.getRecipesByWords("") } returns flow {
            emit(ResponseStatus.Loading())
            emit(ResponseStatus.Success(listOf())) }
    }

    private fun mockError() {
        coEvery { recipesRepository.getRecipesByWords("") } returns flow {
            emit(ResponseStatus.Loading())
            emit(ResponseStatus.Error(1234))
        }
    }
}