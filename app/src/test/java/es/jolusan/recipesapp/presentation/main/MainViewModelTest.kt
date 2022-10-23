package es.jolusan.recipesapp.presentation.main

import es.jolusan.recipesapp.domain.model.RecipeDetail
import es.jolusan.recipesapp.domain.usecases.GetRecipesUseCase
import es.jolusan.recipesapp.utils.ResponseStatus
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Test

class MainViewModelTest {

    @MockK
    private lateinit var getRecipesUseCase: GetRecipesUseCase

    private lateinit var mainViewModel: MainViewModel

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        mainViewModel = MainViewModel(getRecipesUseCase)
        Dispatchers.setMain(Dispatchers.Unconfined)
    }

    @After
    fun onAfter() {
        Dispatchers.resetMain()
    }

    @Test
    fun `when enter search word use case returns values then recipes has success values`() = runTest {
        // Given
        val searchWord = "test_word"
        coEvery { getRecipesUseCase(searchWord) } returns flow {
            emit(ResponseStatus.Success(listOf(recipe1, recipe1)))
        }

        // When
        mainViewModel.getRecipesByWords(searchWord)

        // Then
        coVerify (exactly = 1){ getRecipesUseCase(searchWord) }
        coVerify (exactly = 0){ getRecipesUseCase("") }

        assert(mainViewModel.recipes.value is ResponseStatus.Success)
        assert(mainViewModel.recipes.value.data?.size == 2)
    }

    @Test
    fun `when enter search word use case returns empty then recipes has success empty`() {
        // Given
        coEvery { getRecipesUseCase("") } returns flow {
            emit(ResponseStatus.Success(listOf()))
        }

        // When
        mainViewModel.getRecipesByWords("")

        // Then
        coVerify (exactly = 0){ getRecipesUseCase("searchWord") }
        coVerify (exactly = 1){ getRecipesUseCase("") }

        assert(mainViewModel.recipes.value is ResponseStatus.Success)
        assert(mainViewModel.recipes.value.data?.isEmpty() == true)
    }

    private val recipe1 = RecipeDetail(
        id = "1",
        label = "1",
        description = "1",
        imageURL = "1",
        sourceURL = "1",
        ingredientLines = listOf(),
        calories = 1f,
        mealType = listOf(),
        dishType = listOf()
    )
}