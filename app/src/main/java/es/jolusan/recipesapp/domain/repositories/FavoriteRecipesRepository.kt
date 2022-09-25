package es.jolusan.recipesapp.domain.repositories

import es.jolusan.recipesapp.domain.model.RecipeDetail
import es.jolusan.recipesapp.utils.ResponseStatus
import kotlinx.coroutines.flow.Flow

interface FavoriteRecipesRepository {
    suspend fun getAllFavoriteRecipes(): Flow<ResponseStatus<List<RecipeDetail>>>
    suspend fun insertRecipeInDB(recipe: RecipeDetail)
    suspend fun removeRecipeFromDB(recipeId: String)
    suspend fun isFavoriteRecipe(recipeId: String): Flow<ResponseStatus<Boolean>>
}