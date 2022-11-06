package es.jolusan.recipesapp.presentation.bookmarks

import es.jolusan.recipesapp.domain.model.RecipeDetail
import es.jolusan.recipesapp.domain.usecases.GetFavoriteRecipesUseCase
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

class BookmarksViewModelTest {

    @MockK
    private lateinit var getFavoriteRecipesUseCase: GetFavoriteRecipesUseCase

    private lateinit var bookmarksViewModel: BookmarksViewModel

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        bookmarksViewModel = BookmarksViewModel(getFavoriteRecipesUseCase)
        Dispatchers.setMain(Dispatchers.Unconfined)
    }

    @After
    fun onAfter() {
        Dispatchers.resetMain()
    }

    @Test
    fun `when get recipes in bookmarks and use case returns values then recipes has success values`() = runTest {
        // Given
        coEvery { getFavoriteRecipesUseCase() } returns flow {
            emit(ResponseStatus.Success(listOf(recipe1, recipe1)))
        }

        // When
        bookmarksViewModel.getRecipesSavedInDatabase()

        // Then
        coVerify (exactly = 1){ getFavoriteRecipesUseCase() }

        assert(bookmarksViewModel.recipes.value is ResponseStatus.Success)
        assert(bookmarksViewModel.recipes.value.data?.size == 2)
    }

    @Test
    fun `when get favotite recipes use case returns empty then recipes has success empty`() {
        // Given
        coEvery { getFavoriteRecipesUseCase() } returns flow {
            emit(ResponseStatus.Success(listOf()))
        }

        // When
        bookmarksViewModel.getRecipesSavedInDatabase()

        // Then
        coVerify (exactly = 1){ getFavoriteRecipesUseCase() }

        assert(bookmarksViewModel.recipes.value is ResponseStatus.Success)
        assert(bookmarksViewModel.recipes.value.data?.isEmpty() == true)
    }

    @Test
    fun `when recipe is tapped return a valid recipe object with this id`() = runTest {
        // Given
        coEvery { getFavoriteRecipesUseCase() } returns flow {
            emit(ResponseStatus.Success(listOf(recipe1, recipe2)))
        }

        // When
        bookmarksViewModel.getRecipesSavedInDatabase()

        // Then
        coVerify (exactly = 1){ getFavoriteRecipesUseCase() }
        assert(bookmarksViewModel.recipes.value is ResponseStatus.Success)
        assert(bookmarksViewModel.recipes.value.data?.size == 2)
        assert(bookmarksViewModel.onRecipeClicked("1")?.id == "1")
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

    private val recipe2 = RecipeDetail(
        id = "2",
        label = "2",
        description = "2",
        imageURL = "2",
        sourceURL = "2",
        ingredientLines = listOf(),
        calories = 2f,
        mealType = listOf(),
        dishType = listOf()
    )
}