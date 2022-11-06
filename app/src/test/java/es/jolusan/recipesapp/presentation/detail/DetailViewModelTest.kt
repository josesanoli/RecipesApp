package es.jolusan.recipesapp.presentation.detail

import es.jolusan.recipesapp.domain.model.RecipeDetail
import es.jolusan.recipesapp.domain.usecases.InsertFavoriteRecipeUseCase
import es.jolusan.recipesapp.domain.usecases.IsFavoriteRecipeUseCase
import es.jolusan.recipesapp.domain.usecases.RemoveFavoriteRecipeUseCase
import es.jolusan.recipesapp.utils.ResponseStatus
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.impl.annotations.MockK
import io.mockk.verify
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Test

class DetailViewModelTest {

    @MockK
    private lateinit var isFavoriteRecipeUseCase: IsFavoriteRecipeUseCase
    @MockK
    private lateinit var insertFavoriteRecipeUseCase: InsertFavoriteRecipeUseCase
    @MockK
    private lateinit var removeFavoriteRecipeUseCase: RemoveFavoriteRecipeUseCase

    private lateinit var detailViewModel: DetailViewModel

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        detailViewModel = DetailViewModel(isFavoriteRecipeUseCase, insertFavoriteRecipeUseCase, removeFavoriteRecipeUseCase)
        Dispatchers.setMain(Dispatchers.Unconfined)
    }

    @After
    fun onAfter() {
        Dispatchers.resetMain()
    }

    @Test
    fun `when check if is favourite and use case return true then isFavouriteRecipe is true`() = runTest {
        // Given
        val recipeId = "recipeId"
        coEvery { isFavoriteRecipeUseCase(recipeId) } returns flow {
            emit(ResponseStatus.Success(true))
        }

        // When
        detailViewModel.checkIsFavorite(recipeId)

        // Then
        coVerify (exactly = 1){ isFavoriteRecipeUseCase(recipeId) }

        assert(detailViewModel.isFavoriteRecipe.value is ResponseStatus.Success)
        assert(detailViewModel.isFavoriteRecipe.value.data == true)
    }

    @Test
    fun `when check if is favourite and use case return false then isFavouriteRecipe is false`() = runTest {
        // Given
        val recipeId = "rId"
        coEvery { isFavoriteRecipeUseCase(recipeId) } returns flow {
            emit(ResponseStatus.Success(false))
        }

        // When
        detailViewModel.checkIsFavorite("rId")

        // Then
        coVerify (exactly = 1){ isFavoriteRecipeUseCase("rId") }

        assert(detailViewModel.isFavoriteRecipe.value is ResponseStatus.Success)
        assert(detailViewModel.isFavoriteRecipe.value.data == false)
    }

    @Test
    fun `when add to favorite is called then use case also called`() = runTest {
        // Given
        val recipe = recipe1

        // When
        detailViewModel.addFavorite(recipe)

        // Then
        coVerify (exactly = 1){ insertFavoriteRecipeUseCase(recipe) }
    }

    @Test
    fun `when remove from favorite is called then use case also called`() = runTest {
        // Given
        val recipeId = "recipeId"

        // When
        detailViewModel.removeFavorite(recipeId)

        // Then
        coVerify (exactly = 1){ removeFavoriteRecipeUseCase(recipeId) }
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